package com.alexisboiz.boursewatcher.domain

import android.widget.ImageView
import com.alexisboiz.boursewatcher.domain.datasource.DistantDataSource
import com.alexisboiz.boursewatcher.model.UsersModel.UserDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object AuthRepository {

    fun register(email : String, password : String, avatar : ImageView, firstname :String, lastname : String) : Flow<UserDetail> = flow{
        DistantDataSource.firebaseService.register(email, password, avatar, firstname, lastname).collect{
            emit(it)
        }
    }
}