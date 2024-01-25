package com.alexisboiz.boursewatcher.model.StocksModel

import kotlinx.android.parcel.Parcelize
import java.io.Serializable
@Parcelize

data class CurrentTradingPeriod (

  val pre     : Pre?     = Pre(),
  val regular : Regular? = Regular(),
  val post    : Post?    = Post()

) : Serializable