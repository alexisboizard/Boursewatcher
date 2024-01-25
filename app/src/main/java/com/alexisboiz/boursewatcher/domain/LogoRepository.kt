package com.alexisboiz.boursewatcher.domain

import android.util.Log
import com.alexisboiz.boursewatcher.domain.datasource.LogoDataSource
import com.alexisboiz.boursewatcher.model.LogoModel.LogoList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

object LogoRepository {

    fun getLogo(symbole:String) : Flow<Response<LogoList>> = flow{
        emit(LogoDataSource.logoService.getLogo(symbole))
        Log.d("LogoRepository", "getLogo: ${LogoDataSource.logoService.getLogo(symbole)}")
    }
    fun getLogoVertical(symbole:String) : Flow<Response<LogoList>> = flow{
        emit(LogoDataSource.logoService.getLogoVertical(symbole))
        Log.d("LogoRepository", "getLogo: ${LogoDataSource.logoService.getLogoVertical(symbole)}")
    }
}