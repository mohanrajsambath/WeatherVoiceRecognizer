package com.sathish.weathervoicereco.model


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h")
    val h: Double = 0.0 // 0.93
)