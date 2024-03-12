package com.alexisboiz.boursewatcher.domain

import android.widget.ImageView
import com.alexisboiz.boursewatcher.domain.datasource.DistantDataSource
import com.alexisboiz.boursewatcher.model.UsersModel.UserDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object FavoriteRepository {

    fun putInFirestore(path : String, data : HashMap<String,String?>) : Flow<Boolean> = flow{
         DistantDataSource.firebaseService.putInFirestore(path, data).collect{
             emit(it)
        }
    }

}