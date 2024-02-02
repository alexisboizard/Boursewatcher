package com.alexisboiz.boursewatcher

import com.google.gson.annotations.SerializedName


data class Result (

  @SerializedName("meta"       ) var meta       : Meta?          = Meta(),
  @SerializedName("timestamp"  ) var timestamp  : ArrayList<Int> = arrayListOf(),
  @SerializedName("indicators" ) var indicators : Indicators?    = Indicators()

)