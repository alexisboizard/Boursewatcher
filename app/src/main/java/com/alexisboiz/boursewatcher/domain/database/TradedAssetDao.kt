package com.alexisboiz.boursewatcher.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alexisboiz.boursewatcher.database.entities.TradedAssetEntity

@Dao
interface TradedAssetDao {
    @Insert
    fun insert(TradedAsset: TradedAssetEntity)

    @Query("SELECT * FROM tradedassets WHERE symbol = :symbol")
    fun getTradedAsset(symbol: String): TradedAssetEntity

    @Query("SELECT * FROM tradedassets")
    fun getAllTradedAssets(): List<TradedAssetEntity>

}