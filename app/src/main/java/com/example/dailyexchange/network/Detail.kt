package com.example.dailyexchange.network

import com.squareup.moshi.Json

data class Detail (
    @Json(name = "Exrate") val exrate: Float,
    @Json(name = "UTC") val time: String
    )