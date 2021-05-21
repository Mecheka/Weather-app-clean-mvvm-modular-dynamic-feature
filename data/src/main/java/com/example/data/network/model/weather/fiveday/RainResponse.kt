package com.example.data.network.model.weather.fiveday


import com.google.gson.annotations.SerializedName

data class RainResponse(
    @SerializedName("3h")
    val h: Double?
)