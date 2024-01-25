package com.alexisboiz.boursewatcher.model.StocksModel

import java.io.Serializable


data class Stock (

  val chart : Chart? = Chart()

) : Serializable