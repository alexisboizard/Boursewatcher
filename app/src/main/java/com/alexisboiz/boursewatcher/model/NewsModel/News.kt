package com.alexisboiz.boursewatcher.model.NewsModel

data class News(
    val category: String?,
    var datetime : Int?    = null,
    var headline : String? = null,
    var id       : Int?    = null,
    var image    : String? = null,
    var related  : String? = null,
    var source   : String? = null,
    var summary  : String? = null,
    var url      : String? = null

)
