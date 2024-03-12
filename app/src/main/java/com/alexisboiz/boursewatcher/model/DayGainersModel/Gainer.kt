package com.alexisboiz.boursewatcher.model.DayGainersModel

import com.google.gson.annotations.SerializedName


data class Gainer (

  @SerializedName("finance" ) var finance : Finance? = Finance()

)