package com.alexisboiz.boursewatcher.domain.api

import com.alexisboiz.boursewatcher.model.LogoModel.LogoList
import com.alexisboiz.boursewatcher.model.NewsModel.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LogoService {
    @GET("logos?token=fa880346bb3f4fe1ab00349532022e1e")
    suspend fun getLogo(@Query(value = "symbols") symoble:String) : Response<LogoList>

}