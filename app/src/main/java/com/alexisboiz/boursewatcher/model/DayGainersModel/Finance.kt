package com.alexisboiz.boursewatcher.model.DayGainersModel

import com.google.gson.annotations.SerializedName


data class Finance (

  @SerializedName("result" ) var result : ArrayList<Result> = arrayListOf(),
  @SerializedName("error"  ) var error  : String?           = null

)