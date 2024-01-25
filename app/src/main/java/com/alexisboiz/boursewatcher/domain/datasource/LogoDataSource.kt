package com.alexisboiz.boursewatcher.domain.datasource

import android.util.Log
import com.alexisboiz.boursewatcher.domain.service.LogoService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LogoDataSource {
    private const val BASE_URL = "https://api.benzinga.com/api/v1.1/"

    private val logInterceptor = HttpLoggingInterceptor(){
        Log.d("LogoDataSource", it)
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
    val logoService: LogoService = retrofit.create(LogoService::class.java)
}