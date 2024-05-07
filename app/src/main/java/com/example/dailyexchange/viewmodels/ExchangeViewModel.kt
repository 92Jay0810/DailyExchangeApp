package com.example.dailyexchange.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Insert
import com.example.dailyexchange.database.exchange.Exchange
import com.example.dailyexchange.database.exchange.ExchangeDao
import com.example.dailyexchange.network.SingleForeignExchange
import kotlinx.coroutines.launch

class ExchangeViewModel(private val exchangeDao: ExchangeDao): ViewModel() {

    suspend fun getAll(): List<Exchange> = exchangeDao.getAll()
//    suspend fun insert(exchange: Exchange) = exchangeDao.insertData(exchange)



    fun addExchange(exchangeName: String, exrate: Float, time: String) {
        val addExchange = getInsertExchange(exchangeName, exrate, time)
        insertExchange(addExchange)
    }

    private fun insertExchange(exchange: Exchange) {
        viewModelScope.launch {
            exchangeDao.insertData(exchange)
        }
    }

    private fun getInsertExchange(exchangeName: String, exrate: Float, time: String): Exchange {
        return Exchange(exchange = exchangeName, exrate = exrate, time = time)
    }

    //匯率計算機下拉式選單會改變此值
    var convertCurrencyName:String=""
    //按下button會呼叫此fun並且去計算匯率
    suspend fun getSingleCurrency():Exchange=exchangeDao.getOneCurrency(convertCurrencyName)

    suspend fun calculatorRate(inputNumber:Float):String{
        var selectExrate=getSingleCurrency().exrate.toFloat()
        return (selectExrate*inputNumber).toString()

    }
}

class ExchangeViewModelFactory(private val exchangeDao: ExchangeDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExchangeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExchangeViewModel(exchangeDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}