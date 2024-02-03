package com.alexisboiz.boursewatcher

import com.google.gson.annotations.SerializedName


data class CurrentTradingPeriod (

  @SerializedName("pre"     ) var pre     : Pre?     = Pre(),
  @SerializedName("regular" ) var regular : Regular? = Regular(),
  @SerializedName("post"    ) var post    : Post?    = Post()

)