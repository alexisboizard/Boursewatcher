package com.alexisboiz.boursewatcher.model

import com.google.gson.annotations.SerializedName


data class Resolutions (

  var url    : String? = null,
  var width  : Int?    = null,
  var height : Int?    = null,
  var tag    : String? = null

)