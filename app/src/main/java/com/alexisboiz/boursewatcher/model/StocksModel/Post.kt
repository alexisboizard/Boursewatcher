package com.alexisboiz.boursewatcher.model.StocksModel

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
@Parcelize

data class Post (

  val timezone  : String? = null,
  val end       : Int?    = null,
  val start     : Int?    = null,
  val gmtoffset : Int?    = null

) : Serializable