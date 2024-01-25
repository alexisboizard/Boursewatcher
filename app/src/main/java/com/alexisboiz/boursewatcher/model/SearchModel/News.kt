package com.alexisboiz.boursewatcher.model



data class News (

    var uuid                : String?           = null,
    var title               : String?           = null,
    var publisher           : String?           = null,
    var link                : String?           = null,
    var providerPublishTime : Int?              = null,
    var type                : String?           = null,
    var thumbnail           : Thumbnail?        = Thumbnail(),
    var relatedTickers      : ArrayList<String> = arrayListOf()

)