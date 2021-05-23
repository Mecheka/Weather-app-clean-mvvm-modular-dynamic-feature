package com.example.data.network

import com.example.core.common.DataEntity
import com.example.core.common.ErrorEntity
import com.example.data.BuildConfig
import com.example.data.network.service.OpenWeatherService
import com.example.domain.model.fiveday.FiveDayOpenWeather
import com.example.domain.model.today.ToDayOpenWeather
import com.example.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRepositoryImpl(private val service: OpenWeatherService) : WeatherRepository {
    override suspend fun getWeatherByLocation(
        lat: Double,
        lon: Double
    ): Flow<DataEntity<ToDayOpenWeather>> = flow {
        emit(DataEntity.LOADING)
        try {
            val response = service.getWeatherByLocation(lat, lon, BuildConfig.API_KEY, "metric")
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

    override suspend fun getForecastByLocation(
        lat: Double,
        lon: Double
    ): Flow<DataEntity<FiveDayOpenWeather>> = flow {
        emit(DataEntity.LOADING)
        try {
            val response = service.getForecastWeatherByLocation(lat, lon, BuildConfig.API_KEY, "metric")
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

    override suspend fun getWeatherByCityName(city: String): Flow<DataEntity<ToDayOpenWeather>> = flow {
        emit(DataEntity.LOADING)
        try {
            val response = service.getWeatherByLocation(city, BuildConfig.API_KEY, "metric")
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
