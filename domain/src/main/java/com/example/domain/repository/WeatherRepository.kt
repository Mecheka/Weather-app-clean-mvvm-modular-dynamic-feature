package com.example.domain.repository

import com.example.core.common.DataEntity
import com.example.domain.model.OpenWeather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeatherByCity(lat: Double, lon: Double): Flow<DataEntity<OpenWeather>>
}