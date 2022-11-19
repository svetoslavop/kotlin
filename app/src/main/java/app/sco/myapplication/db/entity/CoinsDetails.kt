package app.sco.myapplication.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinsDetails(
    @PrimaryKey
    @ColumnInfo(name = "id")  var id: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "symbol") var symbol: String,
    @ColumnInfo(name = "current_price") var current_price: Double,
    @ColumnInfo(name = "market_cap")  var market_cap: Long,
    @ColumnInfo(name = "high_24h")  var high_24h: Double,
    @ColumnInfo(name = "price_change_percentage_24h") var price_change_percentage_24h: Double,
    @ColumnInfo(name = "market_cap_change_percentage_24h") var market_cap_change_percentage_24h: Double,
    @ColumnInfo(name = "low_24h") var low_24h: Double,
    @ColumnInfo(name = "favourite") var favourite: Boolean
)