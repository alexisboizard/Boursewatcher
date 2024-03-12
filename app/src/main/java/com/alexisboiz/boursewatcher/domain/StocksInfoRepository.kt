package com.alexisboiz.boursewatcher.domain

import android.content.Context
import com.alexisboiz.boursewatcher.database.AppDatabase
import com.alexisboiz.boursewatcher.database.entities.StocksInfoEntity
import com.alexisboiz.boursewatcher.model.StocksModel.StockInfo

class StocksInfoRepository(ctx : Context) {
    val database = AppDatabase.getInstance(ctx)

    fun insertStockInfo(stockInfo: StockInfo) {
        database!!.tradedAssetDao().insert(
            StocksInfoEntity(
                stockInfo.symbol,
                stockInfo.name,
                stockInfo.logoUrl
            )
        )
    }

    fun getDetails(symbol: String): StockInfo {
        val stockInfo = database?.tradedAssetDao()!!.getDetails(symbol)
        return StockInfo(
            stockInfo.symbol,
            stockInfo.name,
            stockInfo.logoUrl,
        )
    }
}