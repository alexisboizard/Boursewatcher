package com.alexisboiz.boursewatcher.model.LogoModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Logo(
    @Expose
    @SerializedName("name")
    var name : String? = "",
    @Expose
    @SerializedName("ticker")
    var ticker : String? = "",
    @Expose
    @SerializedName("image")
    var url : String
)
