package com.example.dailyexchange.database.exchange

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exchange (
//    @PrimaryKey(autoGenerate = true) val id:Int,
    @PrimaryKey @NonNull @ColumnInfo(name = "Concurrency") val exchange: String,
    @NonNull @ColumnInfo(name = "Exrate") val exrate: Float,
    @NonNull @ColumnInfo(name = "UTC") val time: String
    )