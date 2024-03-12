package com.alexisboiz.boursewatcher.model.StocksModel

import retrofit2.Response


data class RecyclerHorizontalCard(
    val stock : Stock? = Stock(),
    val chartData : ArrayList<Double> = arrayListOf()
)