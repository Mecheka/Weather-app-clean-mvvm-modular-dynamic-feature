package com.example.data.network

import com.example.data.BuildConfig
import com.example.data.network.service.OpenWeatherService
import com.example.domain.common.DataEntity
import com.example.domain.common.ErrorEntity
import com.example.domain.model.OpenWeather
import com.example.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRepositoryImpl(private val service: OpenWeatherService) : WeatherRepository {
    override suspend fun getWeatherByCity(city: String): Flow<DataEntity<OpenWeather>> = flow {
        emit(DataEntity.LOADING)
        try {
            // FIXME: 18/5/2021 AD Change mock city name
            val response = service.getWeatherByCity("Bangkok", BuildConfig.API_KEY)
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