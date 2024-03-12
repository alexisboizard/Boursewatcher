package com.alexisboiz.boursewatcher.model.DayGainersModel

import com.google.gson.annotations.SerializedName


data class Result (

  @SerializedName("id"            ) var id            : String?           = null,
  @SerializedName("title"         ) var title         : String?           = null,
  @SerializedName("start"         ) var start         : Int?              = null,
  @SerializedName("count"         ) var count         : Int?              = null,
  @SerializedName("total"         ) var total         : Int?              = null,
  @SerializedName("quotes"        ) var quotes        : ArrayList<Quotes> = arrayListOf(),
)