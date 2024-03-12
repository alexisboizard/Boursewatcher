package com.alexisboiz.boursewatcher.domain.api

import com.alexisboiz.boursewatcher.model.NewsModel.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("news?token=claf1apr01qi1290vnsgclaf1apr01qi1290vnt0")
    suspend fun getNews(@Query(value = "minId") minId:Int, @Query(value="category")category: String) : Response<List<News>>

}