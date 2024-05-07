package com.example.dailyexchange.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://tw.rter.info/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface ExchangeApiService {
    @GET("capi.php")
    suspend fun getAllExchange(): ForeignExchange
}

object ExchangeApi{
    val retrofitService : ExchangeApiService by lazy {
        retrofit.create(ExchangeApiService::class.java)
    }
}