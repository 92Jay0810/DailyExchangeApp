package com.example.dailyexchange.database.exchange

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dailyexchange.network.SingleForeignExchange


//  這裡要檢查
@Dao
interface ExchangeDao {

    @Query("SELECT * From Exchange")
    suspend fun getAll(): List<Exchange>

    @Query("SELECT * FROM Exchange WHERE Concurrency = :currencyName")
    suspend fun getOneCurrency(currencyName: String): Exchange

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(exchange: Exchange)

    @Delete
    fun delete(exchange: Exchange)
    // suspend fun insertData(exchange: Exchange): Exchange
}