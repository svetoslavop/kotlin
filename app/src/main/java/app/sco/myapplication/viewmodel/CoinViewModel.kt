package app.sco.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import app.sco.myapplication.db.entity.CoinsDetails
import app.sco.myapplication.repository.CoinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CoinViewModel(
    private val coinRepository: CoinRepository
) : ViewModel() {

    private val coinsListStateFlow = MutableStateFlow<List<CoinsDetails>>(arrayListOf())

    private val selectedCoinStateFlow = MutableStateFlow<CoinsDetails?>(null)

    val coinsList: StateFlow<List<CoinsDetails>> = coinsListStateFlow.asStateFlow()

    val selectedCoin: StateFlow<CoinsDetails?> = selectedCoinStateFlow.asStateFlow()

    suspend fun getCoins() {
        val coins = coinRepository.getCoins()
        coinsListStateFlow.value = coins
    }

    suspend fun getCoinByName(name: String) {
        val coin = coinRepository.getCoinByName(name)
        selectedCoinStateFlow.value = coin ?: return
    }

    suspend fun updateFavourites(coin: CoinsDetails) {
        coinRepository.updateCoin(coin)
        selectedCoinStateFlow.value =
            selectedCoinStateFlow.value?.copy(favourite = coin.favourite)
    }
}