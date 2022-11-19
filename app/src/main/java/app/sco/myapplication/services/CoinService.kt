package app.sco.myapplication.services

import app.sco.myapplication.model.CoinDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinService {

    @GET("coins/markets?vs_currency=usd")
    fun getCoins(): Call<List<CoinDetailsResponse>>

}