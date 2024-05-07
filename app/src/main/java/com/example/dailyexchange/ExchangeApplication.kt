package com.example.dailyexchange

import android.app.Application
import com.example.dailyexchange.database.AppDatabase

class ExchangeApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}