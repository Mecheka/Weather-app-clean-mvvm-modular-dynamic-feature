package com.example.domain.usecase

import com.example.core.common.DataEntity
import com.example.core.common.UseCase
import com.example.domain.model.fiveday.FiveDayOpenWeather
import com.example.domain.repository.WeatherRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@FlowPreview
class GetFiveDayWeatherByLocationUseCase(private val repository: WeatherRepository) :
    UseCase<WeatherBody, FiveDayOpenWeather>() {
    override fun validateRequest(request: WeatherBody): WeatherBody = request

    override suspend fun executeRepos(request: WeatherBody): Flow<DataEntity<FiveDayOpenWeather>> =
        repository.getForecastByLocation(request.lat, request.lon)
}