package com.alexisboiz.boursewatcher.domain

import android.util.Log
import com.alexisboiz.boursewatcher.domain.api.LogoDataSource
import com.alexisboiz.boursewatcher.domain.api.NetworkDataSource
import com.alexisboiz.boursewatcher.domain.api.NewsDataSource
import com.alexisboiz.boursewatcher.model.LogoModel.LogoList
import com.alexisboiz.boursewatcher.model.NewsModel.News
import com.alexisboiz.boursewatcher.model.Quotes
import com.alexisboiz.boursewatcher.model.SearchModel
import com.alexisboiz.boursewatcher.model.StocksModel.Stock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Response

object LogoRepository {

    fun getLogo(symbole:String) : Flow<Response<LogoList>> = flow{
        emit(LogoDataSource.logoService.getLogo(symbole))
        Log.d("LogoRepository", "getLogo: ${LogoDataSource.logoService.getLogo(symbole)}")
    }
}