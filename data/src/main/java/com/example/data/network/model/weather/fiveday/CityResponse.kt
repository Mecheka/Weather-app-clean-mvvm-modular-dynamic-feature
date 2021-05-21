package com.example.data.network.model.weather.fiveday


import com.example.data.network.model.weather.CoordResponse
import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("coord")
    val coord: CoordResponse?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("population")
    val population: Int?,
    @SerializedName("sunrise")
    val sunrise: Int?,
    @SerializedName("sunset")
    val sunset: Int?,
    @SerializedName("timezone")
    val timezone: Int?
)