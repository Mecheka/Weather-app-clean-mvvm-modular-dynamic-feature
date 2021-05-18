package com.example.domain.repository

import com.example.domain.common.DataEntity
import com.example.domain.model.OpenWeather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeatherByCity(city: String): Flow<DataEntity<OpenWeather>>
}