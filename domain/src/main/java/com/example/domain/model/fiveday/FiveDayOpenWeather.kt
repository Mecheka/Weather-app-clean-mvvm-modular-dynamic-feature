package com.example.domain.model.fiveday

data class FiveDayOpenWeather(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<Forecast>?,
    val message: Int?
)