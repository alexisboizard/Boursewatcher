package com.alexisboiz.boursewatcher.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alexisboiz.boursewatcher.database.entities.UserEntity

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    fun getUser(email : String, password : String): UserEntity

}