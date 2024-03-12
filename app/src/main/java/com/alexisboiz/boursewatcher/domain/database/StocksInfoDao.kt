package com.alexisboiz.boursewatcher.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alexisboiz.boursewatcher.database.entities.StocksInfoEntity
@Dao
interface StocksInfoDao {
    @Insert
    fun insert(stock: StocksInfoEntity)

    @Query("SELECT * FROM stocks_info WHERE symbol = :symbol")
    fun getDetails(symbol: String): StocksInfoEntity
}