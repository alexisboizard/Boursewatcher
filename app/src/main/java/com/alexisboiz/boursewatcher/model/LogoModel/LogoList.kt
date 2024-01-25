package com.alexisboiz.boursewatcher.model.LogoModel

import com.google.gson.annotations.SerializedName

data class LogoList(
    @SerializedName("logos" ) var logos : ArrayList<Logos> = arrayListOf()
)
