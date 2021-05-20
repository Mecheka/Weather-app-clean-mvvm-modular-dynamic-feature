package com.example.domain.usecase

import com.example.core.common.DataEntity
import com.example.core.common.UseCase
import com.example.domain.model.OpenWeather
import com.example.domain.repository.WeatherRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@FlowPreview
class GetWeatherByCityUseCase(private val repository: WeatherRepository) :
    UseCase<WeatherBody, OpenWeather>() {

    override fun validateRequest(request: WeatherBody): WeatherBody = request

    override suspend fun executeRepos(request: WeatherBody): Flow<DataEntity<OpenWeather>> =
        repository.getWeatherByCity(request.lat, request.lon)
}