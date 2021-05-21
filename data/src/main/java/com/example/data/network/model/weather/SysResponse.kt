package com.example.data.network.model.weather


import com.google.gson.annotations.SerializedName

data class SysResponse(
    @SerializedName("country")
    val country: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("sunrise")
    val sunrise: Int?,
    @SerializedName("sunset")
    val sunset: Int?,
    @SerializedName("type")
    val type: Int?,
    @SerializedName("pod")
    val pod: String?
)