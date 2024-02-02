package com.alexisboiz.boursewatcher

import com.google.gson.annotations.SerializedName


data class Quote (

  @SerializedName("open"   ) var open   : ArrayList<Double> = arrayListOf(),
  @SerializedName("low"    ) var low    : ArrayList<Double> = arrayListOf(),
  @SerializedName("volume" ) var volume : ArrayList<Int>    = arrayListOf(),
  @SerializedName("high"   ) var high   : ArrayList<Double> = arrayListOf(),
  @SerializedName("close"  ) var close  : ArrayList<Double> = arrayListOf()

)