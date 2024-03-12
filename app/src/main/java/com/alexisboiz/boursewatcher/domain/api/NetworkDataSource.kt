package com.alexisboiz.boursewatcher.domain.api

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object NetworkDataSource {
    private const val BASE_URL = "https://query1.finance.yahoo.com/"

    private val logInterceptor = HttpLoggingInterceptor(){
        Log.d("Okhttp", it)
    }.apply{
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttp = OkHttpClient.Builder()
        .addNetworkInterceptor(logInterceptor)
        .build()

    private val retrofit =
        Retrofit.Builder()
            .client(okHttp)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    val stockService: StockService = retrofit.create(StockService::class.java)
}