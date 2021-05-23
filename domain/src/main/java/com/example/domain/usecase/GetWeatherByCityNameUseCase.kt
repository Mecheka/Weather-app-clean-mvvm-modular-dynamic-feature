package com.example.domain.usecase

import com.example.core.common.DataEntity
import com.example.core.common.UseCase
import com.example.domain.model.today.ToDayOpenWeather
import com.example.domain.repository.WeatherRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@FlowPreview
class GetWeatherByCityNameUseCase(private val repository: WeatherRepository) :
    UseCase<String, ToDayOpenWeather>() {
    override fun validateRequest(request: String): String = request

    override suspend fun executeRepos(request: String): Flow<DataEntity<ToDayOpenWeather>> =
        repository.getWeatherByCityName(request)
}