package com.example.data.network.model


import com.example.domain.model.OpenWeather
import com.google.gson.annotations.SerializedName

data class OpenWeatherResponse(
    @SerializedName("base")
    val base: String?,
    @SerializedName("clouds")
    val clouds: Clouds?,
    @SerializedName("cod")
    val cod: Int?,
    @SerializedName("coord")
    val coord: Coord?,
    @SerializedName("dt")
    val dt: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("main")
    val main: Main?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("sys")
    val sys: Sys?,
    @SerializedName("timezone")
    val timezone: Int?,
    @SerializedName("visibility")
    val visibility: Int?,
    @SerializedName("weather")
    val weather: List<Weather>?,
    @SerializedName("wind")
    val wind: Wind?
) {
    
    fun mapToDomain() = OpenWeather(
        base = base,
        clouds = com.example.domain.model.Clouds(clouds?.all),
        cod = cod,
        coord = com.example.domain.model.Coord(
            coord?.lat,
            coord?.lon
        ),
        dt = dt,
        id = id,
        main = com.example.domain.model.Main(
            feels_like = main?.feelsLike,
            humidity = main?.humidity,
            pressure = main?.pressure,
            temp = main?.temp,
            tempMax = main?.tempMax,
            tempMin = main?.tempMin
        ),
        name = name,
        sys = com.example.domain.model.Sys(
            country = sys?.country,
            id = sys?.id,
            sunrise = sys?.sunrise,
            sunset = sys?.sunset,
            type = sys?.type
        ),
        timezone = timezone,
        visibility = visibility,
        weather = weather?.map {
            com.example.domain.model.Weather(
                description = it.description,
                icon = it.icon,
                id = it.id,
                main = it.main
            )
        } ?: listOf(),
        wind = com.example.domain.model.Wind(
            deg = wind?.deg,
            gust = wind?.gust,
            speed = wind?.speed
        )
    )
}