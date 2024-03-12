package com.alexisboiz.boursewatcher.model.DayGainersModel

import com.google.gson.annotations.SerializedName


data class Quotes (
  @SerializedName("regularMarketChangePercent"        ) var regularMarketChangePercent        : Double?  = null,
  @SerializedName("averageAnalystRating"              ) var averageAnalystRating              : String?  = null,
  @SerializedName("exchange"                          ) var exchange                          : String?  = null,
  @SerializedName("priceHint"                         ) var priceHint                         : Int?     = null,
  @SerializedName("regularMarketChange"               ) var regularMarketChange               : Double?   = null,
  @SerializedName("regularMarketTime"                 ) var regularMarketTime                 : Int?     = null,
  @SerializedName("regularMarketPrice"                ) var regularMarketPrice                : Double?  = null,
  @SerializedName("regularMarketDayHigh"              ) var regularMarketDayHigh              : Double?  = null,
  @SerializedName("regularMarketDayRange"             ) var regularMarketDayRange             : String?  = null,
  @SerializedName("regularMarketDayLow"               ) var regularMarketDayLow               : Double?  = null,
  @SerializedName("regularMarketVolume"               ) var regularMarketVolume               : Int?     = null,
  @SerializedName("regularMarketPreviousClose"        ) var regularMarketPreviousClose        : Double?  = null,
  @SerializedName("fullExchangeName"                  ) var fullExchangeName                  : String?  = null,
  @SerializedName("longName"                          ) var longName                          : String?  = null,
  @SerializedName("shortName"                         ) var shortName                          : String?  = null,
  @SerializedName("financialCurrency"                 ) var financialCurrency                 : String?  = null,
  @SerializedName("marketCap"                         ) var marketCap                         : Long?     = null,
  @SerializedName("symbol"                            ) var symbol                            : String?  = null,
  @SerializedName("currency"                          ) var currency                          : String?  = null,

)