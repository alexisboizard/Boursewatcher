package com.alexisboiz.boursewatcher.model.StocksModel


data class Result (

    val meta       : Meta?          = Meta(),
    val timestamp  : ArrayList<Int> = arrayListOf(),
    val indicators : Indicators?    = Indicators()

)