package com.alexisboiz.boursewatcher.database.entities

import androidx.room.Entity

@Entity(
    tableName = "stocks_info",
    primaryKeys = ["symbol"],
)

data class StocksInfoEntity(
    val symbol : String,
    val name : String?,
    val logoUrl : String?,
)
