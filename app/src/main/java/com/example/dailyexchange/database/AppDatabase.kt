package com.example.dailyexchange.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dailyexchange.database.exchange.Exchange
import com.example.dailyexchange.database.exchange.ExchangeDao

@Database(entities = arrayOf(Exchange::class), version = 3)
abstract class AppDatabase: RoomDatabase(){
    abstract fun exchangeDao(): ExchangeDao

    // 這裡刪除了 createFromAsset
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context, AppDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance

                instance
            }
        }
    }
}