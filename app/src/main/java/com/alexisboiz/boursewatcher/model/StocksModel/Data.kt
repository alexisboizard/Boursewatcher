package com.alexisboiz.boursewatcher

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("chart" ) var chart : Chart? = Chart()

)