package app.sco.myapplication.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.sco.myapplication.repository.CoinRepository
import app.sco.myapplication.viewmodel.CoinViewModel


class CoinViewModelFactory(
    private val coinRepository: CoinRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CoinViewModel(coinRepository) as T
    }
}