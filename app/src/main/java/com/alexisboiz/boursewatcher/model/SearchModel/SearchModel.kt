package com.alexisboiz.boursewatcher.model

data class SearchModel (

  var explains                       : ArrayList<String> = arrayListOf(),
  var count                          : Int?              = null,
  var quotes                         : ArrayList<Quotes> = arrayListOf(),
  var news                           : ArrayList<News>   = arrayListOf(),
  var nav                            : ArrayList<String> = arrayListOf(),
  var lists                          : ArrayList<String> = arrayListOf(),
  var researchReports                : ArrayList<String> = arrayListOf(),
  var screenerFieldResults           : ArrayList<String> = arrayListOf(),
  var totalTime                      : Int?              = null,
  var timeTakenForQuotes             : Int?              = null,
  var timeTakenForNews               : Int?              = null,
  var timeTakenForAlgowatchlist      : Int?              = null,
  var timeTakenForPredefinedScreener : Int?              = null,
  var timeTakenForCrunchbase         : Int?              = null,
  var timeTakenForNav                : Int?              = null,
  var timeTakenForResearchReports    : Int?              = null,
  var timeTakenForScreenerField      : Int?              = null,
  var timeTakenForCulturalAssets     : Int?              = null

)