package com.alexisboiz.boursewatcher.model



data class Quotes (

  var exchange       : String?  = null,
  var shortname      : String?  = null,
  var quoteType      : String?  = null,
  var symbol         : String?  = null,
  var index          : String?  = null,
  var score          : Int?     = null,
  var typeDisp       : String?  = null,
  var longname       : String?  = null,
  var exchDisp       : String?  = null,
  var sector         : String?  = null,
  var sectorDisp     : String?  = null,
  var industry       : String?  = null,
  var industryDisp   : String?  = null,
  var dispSecIndFlag : Boolean? = null,
  var isYahooFinance : Boolean? = null

)