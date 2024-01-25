package com.alexisboiz.boursewatcher.domain.service

import com.alexisboiz.boursewatcher.model.LogoModel.LogoList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LogoService {
    @GET("logos?token=fa880346bb3f4fe1ab00349532022e1e")
    suspend fun getLogo(@Query(value = "symbols") symoble:String) : Response<LogoList>

    @GET("logos?token=fa880346bb3f4fe1ab00349532022e1e")
    suspend fun getLogoVertical(@Query(value = "symbols") symoble:String) : Response<LogoList>

}