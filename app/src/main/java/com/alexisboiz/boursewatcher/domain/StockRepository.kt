package com.alexisboiz.boursewatcher.domain

import android.util.Log
import com.alexisboiz.boursewatcher.domain.api.NetworkDataSource
import com.alexisboiz.boursewatcher.model.DayGainersModel.Gainer
import com.alexisboiz.boursewatcher.model.Quotes
import com.alexisboiz.boursewatcher.model.SearchModel
import com.alexisboiz.boursewatcher.model.StocksModel.Stock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Response

object StockRepository {

    suspend fun getStock(symbole:String) : Flow<Response<Stock>> = flow{
        emit(NetworkDataSource.stockService.getStock(symbole))
    }
    suspend fun find(keyword:String) : Flow<Response<SearchModel>> = flow{
        emit(NetworkDataSource.stockService.getSearch(keyword))
    }

    suspend fun getDayGainers() : Flow<Response<Gainer>> = flow{
        emit(NetworkDataSource.stockService.getDayGainers())
        Log.e("StockRepository", "getDayGainers: ${NetworkDataSource.stockService.getDayGainers()}")
    }
}