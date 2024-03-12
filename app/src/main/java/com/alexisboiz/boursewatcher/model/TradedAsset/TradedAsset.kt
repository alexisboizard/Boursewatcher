package com.alexisboiz.boursewatcher.model.TradedAsset

data class TradedAsset(
    val symbol: String,
    val purshasedPrice: Double,
    val quantity: Double,
    val purshasedDate: String,
)
