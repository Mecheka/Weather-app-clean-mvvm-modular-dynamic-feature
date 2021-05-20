package com.example.data.network

import com.example.data.BuildConfig
import com.example.data.network.service.OpenWeatherService
import com.example.core.common.DataEntity
import com.example.core.common.ErrorEntity
import com.example.domain.model.OpenWeather
import com.example.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRepositoryImpl(private val service: OpenWeatherService) : WeatherRepository {
    override suspend fun getWeatherByCity(lat: Double, lon: Double): Flow<DataEntity<OpenWeather>> = flow {
        emit(DataEntity.LOADING)
        try {
            val response = service.getWeatherByCity(lat, lon, BuildConfig.API_KEY, "metric")
            if (response.isSuccessful) {
                emit(
                    DataEntity.SUCCESS(
                        response.body()?.mapToDomain()
                    )
                )
            } else {
                emit(DataEntity.ERROR(ErrorEntity(response.message())))
            }
        } catch (e: Exception) {
            emit(DataEntity.ERROR(ErrorEntity(e.message)))
        }
    }
}
