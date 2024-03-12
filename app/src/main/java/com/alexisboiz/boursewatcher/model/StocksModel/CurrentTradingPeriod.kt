package com.alexisboiz.boursewatcher.model.StocksModel

import java.io.Serializable

data class CurrentTradingPeriod (

  val pre     : Pre?     = Pre(),
  val regular : Regular? = Regular(),
  val post    : Post?    = Post()

) : Serializable