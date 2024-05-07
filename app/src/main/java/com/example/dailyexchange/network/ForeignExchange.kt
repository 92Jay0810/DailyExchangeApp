package com.example.dailyexchange.network

import com.squareup.moshi.Json

data class ForeignExchange (
    @Json(name = "USDFKP") val USDFKP: Detail,
    @Json(name = "USDITL") val USDITL: Detail,
    @Json(name = "USDCNH") val USDCNH: Detail,
    @Json(name = "USDLAK") val USDLAK: Detail,
    @Json(name = "USDZMW") val USDZMW: Detail,
    @Json(name = "USDAOA") val USDAOA: Detail,
    @Json(name = "USDTWD") val USDTWD: Detail
    )