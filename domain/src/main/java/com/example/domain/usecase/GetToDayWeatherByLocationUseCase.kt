package com.example.domain.usecase

import com.example.core.common.DataEntity
import com.example.core.common.UseCase
import com.example.domain.model.today.ToDayOpenWeather
import com.example.domain.repository.WeatherRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@FlowPreview
class GetToDayWeatherByLocationUseCase(private val repository: WeatherRepository) :
    UseCase<WeatherBody, ToDayOpenWeather>() {

    override fun validateRequest(request: WeatherBody): WeatherBody = request

    override suspend fun executeRepos(request: WeatherBody): Flow<DataEntity<ToDayOpenWeather>> =
        repository.getWeatherByLocation(request.lat, request.lon)
}