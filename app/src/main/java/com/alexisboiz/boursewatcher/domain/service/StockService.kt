package com.alexisboiz.boursewatcher.domain.service

import com.alexisboiz.boursewatcher.model.DayGainersModel.Gainer
import com.alexisboiz.boursewatcher.model.SearchModel
import com.alexisboiz.boursewatcher.model.StocksModel.Stock
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StockService {
    @GET("v8/finance/chart/{symbole}?region=FR&lang=fr-FR")
    suspend fun getStock(@Path("symbole")symbole:String) : Response<Stock>

    @GET("v1/finance/search")
    suspend fun getSearch(@Query(value = "q")q:String) : Response<SearchModel>

    @GET("v1/finance/screener/predefined/saved?scrIds=day_gainers&count=50")
    suspend fun getDayGainers() : Response<Gainer>
}