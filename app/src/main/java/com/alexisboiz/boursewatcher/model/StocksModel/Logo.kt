package com.alexisboiz.boursewatcher

import com.google.gson.annotations.SerializedName


data class Logo (

  @SerializedName("name"   ) var name   : String? = null,
  @SerializedName("ticker" ) var ticker : String? = null,
  @SerializedName("image"  ) var image  : String? = null

)