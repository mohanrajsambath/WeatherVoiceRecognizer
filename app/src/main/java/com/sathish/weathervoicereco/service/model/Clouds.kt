package com.sathish.weathervoicereco.service.model


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int = 0 // 90
)