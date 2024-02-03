package com.alexisboiz.boursewatcher

import com.google.gson.annotations.SerializedName


data class Pre (

  @SerializedName("timezone"  ) var timezone  : String? = null,
  @SerializedName("end"       ) var end       : Int?    = null,
  @SerializedName("start"     ) var start     : Int?    = null,
  @SerializedName("gmtoffset" ) var gmtoffset : Int?    = null

)