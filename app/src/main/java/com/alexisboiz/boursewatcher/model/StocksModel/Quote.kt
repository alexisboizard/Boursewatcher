package com.alexisboiz.boursewatcher.model.StocksModel

import java.io.Serializable


data class Quote (

  val low    : ArrayList<Double> = arrayListOf(),
  val close  : ArrayList<Double> = arrayListOf(),
  val high   : ArrayList<Double> = arrayListOf(),
  val volume : ArrayList<Int>    = arrayListOf(),
  val open   : ArrayList<Double> = arrayListOf()

) : Serializable