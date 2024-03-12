package com.alexisboiz.boursewatcher.model.StocksModel



data class Chart (

    val result : ArrayList<Result> = arrayListOf(),
    val error  : String?           = null

)