package com.alexisboiz.boursewatcher.domain.service

import com.alexisboiz.boursewatcher.StockDetails
import com.alexisboiz.boursewatcher.model.Gainers
import com.alexisboiz.boursewatcher.model.SearchModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StockService {
    @GET("stocks")
    suspend fun getStock(@Query("symbol")symbole:String) : Response<StockDetails>

    @GET("search")
    suspend fun getSearch(@Query(value = "symbol")q:String) : Response<SearchModel>

    @GET("daygainers")
    suspend fun getDayGainers() : Response<ArrayList<Gainers>>
}