package com.alexisboiz.boursewatcher.domain.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsDataSource {
    private const val BASE_URL = "https://finnhub.io/api/v1/"

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

    val newsService: NewsService = retrofit.create(NewsService::class.java)
}