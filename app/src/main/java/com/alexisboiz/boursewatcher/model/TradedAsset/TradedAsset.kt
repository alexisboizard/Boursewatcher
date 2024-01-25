package com.alexisboiz.boursewatcher.model.TradedAsset

data class TradedAsset(
    val id : String = "",
    val symbol: String,
    val purshasedPrice: Double,
    val quantity: Double,
    val purshasedDate: String,
)
