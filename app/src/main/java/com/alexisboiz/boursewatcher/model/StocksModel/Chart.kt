package com.alexisboiz.boursewatcher

import com.google.gson.annotations.SerializedName


data class Chart (

  @SerializedName("result" ) var result : ArrayList<Result> = arrayListOf(),
  @SerializedName("error"  ) var error  : String?           = null

)