package app.sco.myapplication.repository

import android.content.Context
import app.sco.myapplication.db.dao.CoinsDao
import app.sco.myapplication.db.entity.CoinsDetails
import app.sco.myapplication.model.CoinDetailsResponse
import app.sco.myapplication.services.CoinService
import app.sco.myapplication.util.NetworkUtil

class CoinRepository constructor(
    private val context: Context,
    private val coinService: CoinService,
    private val coinDao: CoinsDao
) {
    suspend fun getCoins(): List<CoinsDetails> {
        return try {
            // if Internet connection is available fetch countries, save them to DB and return them
            if (NetworkUtil.isConnected(context)) {
                // execute the network request synchronously in order to wait for the response and handle it
                val coins = coinService.getCoins().execute().body()
                val roomCoins = coins?.map { mapResponseToDbModel(it) }
                coinDao.insertAll(roomCoins ?: return arrayListOf())

            }
            coinDao.getCoins()
        } catch (e: Exception) {
            arrayListOf()
        }
    }

    suspend fun getCoinByName(name: String): CoinsDetails? {
        return try {

            return coinDao.getCoinByName(name)

        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateCoin(country: CoinsDetails) {
        coinDao.update(country)
    }

    private fun mapResponseToDbModel(response: CoinDetailsResponse): CoinsDetails {
        return CoinsDetails(
            id = response.id ?: "",
            name = response.name ?: "",
            image = response.image ?: "",
            symbol = response.symbol ?: "",
            current_price = response.current_price ?: 0.00,
            market_cap = response.market_cap ?: 0,
            high_24h= response.high_24h ?: 0.00,
            price_change_percentage_24h = response.price_change_percentage_24h ?: 0.00,
            market_cap_change_percentage_24h = response.market_cap_change_percentage_24h ?: 0.00,
            low_24h = response.low_24h ?: 0.00,
            favourite = false
        )
    }
}