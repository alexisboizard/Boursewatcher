package com.alexisboiz.boursewatcher.domain

import com.alexisboiz.boursewatcher.StockDetails
import com.alexisboiz.boursewatcher.domain.datasource.NetworkDataSource
import com.alexisboiz.boursewatcher.model.Gainers
import com.alexisboiz.boursewatcher.model.SearchModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

object StockRepository {

    fun getStock(symbole:String) : Flow<Response<StockDetails>> = flow{
        emit(NetworkDataSource.stockService.getStock(symbole))
    }
        fun find(keyword:String) : Flow<Response<SearchModel>> = flow{
        emit(NetworkDataSource.stockService.getSearch(keyword))
    }

    fun getDayGainers() : Flow<Response<ArrayList<Gainers>>> = flow{
        emit(NetworkDataSource.stockService.getDayGainers())
    }
}