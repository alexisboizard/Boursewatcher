package com.alexisboiz.boursewatcher.model.StocksModel

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class TradingPeriods (

  val timezone  : String? = null,
  val end       : Int?    = null,
  val start     : Int?    = null,
  val gmtoffset : Int?    = null

) : Serializable