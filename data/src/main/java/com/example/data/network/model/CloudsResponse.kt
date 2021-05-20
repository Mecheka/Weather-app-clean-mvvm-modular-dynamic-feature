package com.example.data.network.model


import com.google.gson.annotations.SerializedName

data class CloudsResponse(
    @SerializedName("all")
    val all: Int?
)