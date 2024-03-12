package com.alexisboiz.boursewatcher.model.StocksModel

import java.io.Serializable

data class Meta (

    val currency             : String?                              = null,
    val symbol               : String?                              = null,
    val exchangeName         : String?                              = null,
    val instrumentType       : String?                              = null,
    val firstTradeDate       : Int?                                 = null,
    val regularMarketTime    : Int?                                 = null,
    val gmtoffset            : Int?                                 = null,
    val timezone             : String?                              = null,
    val exchangeTimezoneName : String?                              = null,
    val regularMarketPrice   : Double?                              = null,
    val chartPreviousClose   : Double?                              = null,
    val previousClose        : Double?                              = null,
    val scale                : Int?                                 = null,
    val priceHint            : Int?                                 = null,
    val currentTradingPeriod : CurrentTradingPeriod?                = CurrentTradingPeriod(),
    val tradingPeriods       : ArrayList<ArrayList<TradingPeriods>> = arrayListOf(),
    val dataGranularity      : String?                              = null,
    val range                : String?                              = null,
    val validRanges          : ArrayList<String>                    = arrayListOf()

) : Serializable