package com.alexisboiz.boursewatcher.domain

import android.content.Context
import com.alexisboiz.boursewatcher.database.AppDatabase
import com.alexisboiz.boursewatcher.database.entities.TradedAssetEntity
import com.alexisboiz.boursewatcher.model.TradedAsset.TradedAsset

class TradedAssetRepository(ctx : Context) {
    val database = AppDatabase.getInstance(ctx)

    fun insertTradedAsset(tradedAsset: TradedAsset) {
        val tradedAssetEntity = TradedAssetEntity(
            tradedAsset.symbol,
            tradedAsset.purshasedPrice,
            tradedAsset.quantity,
            tradedAsset.purshasedDate
        )
        database!!.tradedAssetDao().insert(tradedAssetEntity)
    }

    fun getTradedAsset(symbol: String): TradedAsset {
        val tradedAssetEntity = database?.tradedAssetDao()!!.getTradedAsset(symbol)
        return TradedAsset(
            "",
            tradedAssetEntity.symbol,
            tradedAssetEntity.purshasedPrice,
            tradedAssetEntity.quantity,
            tradedAssetEntity.purshasedDate,
        )
    }

    fun getAllTradedAsset() : List<TradedAsset>{
        val tradedAssetEntity = database?.tradedAssetDao()!!.getAllTradedAssets()
        val tradedAssetList = mutableListOf<TradedAsset>()
        for (tradedAsset in tradedAssetEntity!!){
            tradedAssetList.add(
                TradedAsset(
                    "",
                    tradedAsset.symbol,
                    tradedAsset.purshasedPrice,
                    tradedAsset.quantity,
                    tradedAsset.purshasedDate,
                )
            )
        }
        return tradedAssetList
    }
}