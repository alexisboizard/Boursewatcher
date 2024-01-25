package com.alexisboiz.boursewatcher.model.StocksModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Indicators (

  @SerializedName("quote" ) var quote : ArrayList<Quote> = arrayListOf()

) : Serializable