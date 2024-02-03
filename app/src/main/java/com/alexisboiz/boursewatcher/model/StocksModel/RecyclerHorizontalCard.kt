package com.alexisboiz.boursewatcher.model.StocksModel

import com.alexisboiz.boursewatcher.StockDetails
import java.io.Serializable

data class RecyclerHorizontalCard(
    val stock : StockDetails? = StockDetails(),
    val chartData : ArrayList<Double> = arrayListOf(),
) : Serializable