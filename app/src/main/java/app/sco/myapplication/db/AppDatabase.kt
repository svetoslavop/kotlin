package app.sco.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.sco.myapplication.db.dao.CoinsDao
import app.sco.myapplication.db.entity.CoinsDetails


@Database(entities = [CoinsDetails::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun CoinsDao(): CoinsDao
}