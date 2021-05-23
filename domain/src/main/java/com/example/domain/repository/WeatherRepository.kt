package com.example.domain.repository

import com.example.core.common.DataEntity
import com.example.domain.model.fiveday.FiveDayOpenWeather
import com.example.domain.model.today.ToDayOpenWeather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeatherByLocation(lat: Double, lon: Double): Flow<DataEntity<ToDayOpenWeather>>

    suspend fun getForecastByLocation(lat: Double, lon: Double): Flow<DataEntity<FiveDayOpenWeather>>

    suspend fun getWeatherByCityName(city: String): Flow<DataEntity<ToDayOpenWeather>>
}