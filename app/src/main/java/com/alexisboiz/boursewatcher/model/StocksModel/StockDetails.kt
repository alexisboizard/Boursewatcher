package com.alexisboiz.boursewatcher

import com.google.gson.annotations.SerializedName


data class StockDetails (

  @SerializedName("data" ) var data : Data?           = Data(),
  @SerializedName("logo" ) var logo : ArrayList<Logo> = arrayListOf()

)