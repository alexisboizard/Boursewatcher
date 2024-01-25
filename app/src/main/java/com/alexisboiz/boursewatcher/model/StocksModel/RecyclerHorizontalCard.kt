package com.alexisboiz.boursewatcher.model.StocksModel

import java.io.Serializable

data class RecyclerHorizontalCard(
    val stock : Stock? = Stock(),
    val chartData : ArrayList<Double> = arrayListOf(),
    var logoUrl : String
) : Serializable