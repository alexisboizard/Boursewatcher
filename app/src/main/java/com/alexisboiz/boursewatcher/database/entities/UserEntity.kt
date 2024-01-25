package com.alexisboiz.boursewatcher.database.entities

import androidx.room.Entity
import androidx.room.Index
import org.jetbrains.annotations.NotNull

@Entity(
    tableName = "users",
    primaryKeys = ["email", "password"],
    indices = [Index(value = ["email"], unique = true)]
)

data class UserEntity(
    val email: String,
    val username: String,
    val password: String
)
