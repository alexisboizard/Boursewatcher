package com.alexisboiz.boursewatcher.domain

import android.util.Log
import com.alexisboiz.boursewatcher.domain.datasource.NewsDataSource
import com.alexisboiz.boursewatcher.model.NewsModel.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

object NewsRepository {

    fun getNews(minId:Int, category: String) : Flow<Response<List<News>>> = flow{
        emit(NewsDataSource.newsService.getNews(minId,category))
        Log.d("NewsRepository", "getNews: ${NewsDataSource.newsService.getNews(minId,category)}")
    }
}