package com.example.data.network.service

import com.example.data.network.model.weather.fiveday.FiveDayOpenWeatherResponse
import com.example.data.network.model.weather.today.ToDayOpenWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    @GET("weather")
    suspend fun getWeatherByLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Response<ToDayOpenWeatherResponse>

    @GET("forecast")
    suspend fun getForecastWeatherByLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Response<FiveDayOpenWeatherResponse>

    @GET("weather")
    suspend fun getWeatherByLocation(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Response<ToDayOpenWeatherResponse>
}