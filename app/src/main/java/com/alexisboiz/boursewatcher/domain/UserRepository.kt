package com.alexisboiz.boursewatcher.domain

import android.content.Context
import com.alexisboiz.boursewatcher.database.AppDatabase
import com.alexisboiz.boursewatcher.database.entities.UserEntity
import com.alexisboiz.boursewatcher.domain.database.UserDao
import com.alexisboiz.boursewatcher.model.UsersModel.User

class UserRepository(ctx : Context) {
    val database = AppDatabase.getInstance(ctx)

    fun insertUser(user: User) {
        val userEntity = UserEntity(
            user.email,
            user.username,
            user.password
        )
        database!!.userDao().insertUser(userEntity)
    }

    fun getUser(email : String, password : String) : User{
        val userEntity = database?.userDao()?.getUser(email,password)
        var user = User("","","")
        if (userEntity != null) {
            user =  User(
                userEntity.email,
                userEntity.username,
                userEntity.password,
            )
        }
        return user
    }
}