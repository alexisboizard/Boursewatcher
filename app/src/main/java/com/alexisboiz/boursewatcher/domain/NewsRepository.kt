package com.alexisboiz.boursewatcher.domain

import android.util.Log
import com.alexisboiz.boursewatcher.domain.api.NetworkDataSource
import com.alexisboiz.boursewatcher.domain.api.NewsDataSource
import com.alexisboiz.boursewatcher.model.NewsModel.News
import com.alexisboiz.boursewatcher.model.Quotes
import com.alexisboiz.boursewatcher.model.SearchModel
import com.alexisboiz.boursewatcher.model.StocksModel.Stock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Response

object NewsRepository {

    fun getNews(minId:Int, category: String) : Flow<Response<List<News>>> = flow{
        emit(NewsDataSource.newsService.getNews(minId,category))
        Log.d("NewsRepository", "getNews: ${NewsDataSource.newsService.getNews(minId,category)}")
    }
}