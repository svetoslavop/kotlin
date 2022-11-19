package app.sco.myapplication.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.sco.myapplication.db.entity.CoinsDetails

@Dao
interface CoinsDao {

    @Query("SELECT * FROM coins")
    suspend fun getCoins(): List<CoinsDetails>

    @Query("SELECT * FROM coins WHERE name=:name")
    suspend fun getCoinByName(name: String): CoinsDetails

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(coins: List<CoinsDetails>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(coin: CoinsDetails)
}