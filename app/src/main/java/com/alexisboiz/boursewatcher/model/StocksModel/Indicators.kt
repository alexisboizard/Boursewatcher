package com.alexisboiz.boursewatcher

import com.google.gson.annotations.SerializedName


data class Indicators (

  @SerializedName("quote" ) var quote : ArrayList<Quote> = arrayListOf()

)