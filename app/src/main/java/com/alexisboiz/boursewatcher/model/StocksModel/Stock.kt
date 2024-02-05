package com.alexisboiz.boursewatcher.model.StocksModel

import com.alexisboiz.boursewatcher.Chart
import java.io.Serializable


data class Stock (

  val chart : Chart? = Chart()

) : Serializable