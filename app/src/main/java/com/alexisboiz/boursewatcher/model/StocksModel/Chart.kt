package com.alexisboiz.boursewatcher.model.StocksModel

import java.io.Serializable



data class Chart (

    val result : ArrayList<Result> = arrayListOf(),
    val error  : String?           = null

) : Serializable