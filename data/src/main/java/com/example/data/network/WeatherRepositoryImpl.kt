package com.example.data.network

import com.example.core.common.DataEntity
import com.example.data.BuildConfig
import com.example.core.utils.flowOfWrapInnovation
import com.example.data.network.service.OpenWeatherService
import com.example.domain.model.fiveday.FiveDayOpenWeather
import com.example.domain.model.today.ToDayOpenWeather
import com.example.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class WeatherRepositoryImpl(private val service: OpenWeatherService) : WeatherRepository {
    override suspend fun getWeatherByLocation(
        lat: Double,
        lon: Double
    ): Flow<DataEntity<ToDayOpenWeather>> = flowOfWrapInnovation(
        service.getWeatherByLocation(
            lat,
            lon,
            BuildConfig.API_KEY,
            "metric"
        ),
        mapper = {
            it?.mapToDomain()
        }
    )

    override suspend fun getForecastByLocation(
        lat: Double,
        lon: Double
    ): Flow<DataEntity<FiveDayOpenWeather>> = flowOfWrapInnovation(
        service.getForecastWeatherByLocation(
            lat,
            lon,
            BuildConfig.API_KEY,
            "metric"
        ), mapper = { it?.mapToDomain() })

    override suspend fun getWeatherByCityName(city: String): Flow<DataEntity<ToDayOpenWeather>> =
        flowOfWrapInnovation(
            service.getWeatherByLocation(city, BuildConfig.API_KEY, "metric"),
            mapper = {
                it?.mapToDomain()
            }
        )
}
