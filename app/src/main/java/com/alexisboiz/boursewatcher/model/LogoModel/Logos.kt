package com.alexisboiz.boursewatcher.model.LogoModel

import com.google.gson.annotations.SerializedName

data class Logos (

    @SerializedName("created"  ) var created  : String?   = null,
    @SerializedName("updated"  ) var updated  : String?   = null,
    @SerializedName("files"    ) var files    : Files?    = Files(),
)
