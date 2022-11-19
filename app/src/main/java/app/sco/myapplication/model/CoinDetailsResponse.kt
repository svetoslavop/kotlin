package app.sco.myapplication.model

data class CoinDetailsResponse(
    val id: String,
    val name: String,
    val image: String,
    val symbol: String,
    val current_price: Double,
    val market_cap: Long,
    val high_24h: Double,
    val price_change_percentage_24h: Double,
    val market_cap_change_percentage_24h: Double,
    val low_24h: Double,
)