package com.alexisboiz.boursewatcher

import com.google.gson.annotations.SerializedName


data class Meta (

  @SerializedName("currency"             ) var currency             : String?                              = null,
  @SerializedName("symbol"               ) var symbol               : String?                              = null,
  @SerializedName("exchangeName"         ) var exchangeName         : String?                              = null,
  @SerializedName("instrumentType"       ) var instrumentType       : String?                              = null,
  @SerializedName("firstTradeDate"       ) var firstTradeDate       : Int?                                 = null,
  @SerializedName("regularMarketTime"    ) var regularMarketTime    : Int?                                 = null,
  @SerializedName("gmtoffset"            ) var gmtoffset            : Int?                                 = null,
  @SerializedName("timezone"             ) var timezone             : String?                              = null,
  @SerializedName("exchangeTimezoneName" ) var exchangeTimezoneName : String?                              = null,
  @SerializedName("regularMarketPrice"   ) var regularMarketPrice   : Double?                              = null,
  @SerializedName("chartPreviousClose"   ) var chartPreviousClose   : Double?                              = null,
  @SerializedName("previousClose"        ) var previousClose        : Double?                              = null,
  @SerializedName("scale"                ) var scale                : Int?                                 = null,
  @SerializedName("priceHint"            ) var priceHint            : Int?                                 = null,
  @SerializedName("currentTradingPeriod" ) var currentTradingPeriod : CurrentTradingPeriod?                = CurrentTradingPeriod(),
  @SerializedName("tradingPeriods"       ) var tradingPeriods       : ArrayList<ArrayList<TradingPeriods>> = arrayListOf(),
  @SerializedName("dataGranularity"      ) var dataGranularity      : String?                              = null,
  @SerializedName("range"                ) var range                : String?                              = null,
  @SerializedName("validRanges"          ) var validRanges          : ArrayList<String>                    = arrayListOf()

)