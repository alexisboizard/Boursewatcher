package com.alexisboiz.boursewatcher.database.entities

import androidx.room.Entity

@Entity(
    tableName = "tradedassets",
    primaryKeys = ["symbol"],
)

data class TradedAssetEntity(
    val symbol : String,
    val purshasedPrice : Double,
    val quantity : Double,
    val purshasedDate : String,
)
