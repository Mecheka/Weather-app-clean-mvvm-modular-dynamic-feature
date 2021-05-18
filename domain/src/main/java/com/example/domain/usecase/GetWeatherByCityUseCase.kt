package com.example.domain.usecase

import com.example.domain.common.DataEntity
import com.example.domain.common.UseCase
import com.example.domain.model.OpenWeather
import com.example.domain.repository.WeatherRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

@FlowPreview
class GetWeatherByCityUseCase(private val repository: WeatherRepository): UseCase<Unit, OpenWeather>() {

    override fun validateRequest(request: Unit) = request

    override suspend fun executeRepos(request: Unit): Flow<DataEntity<OpenWeather>> = repository.getWeatherByCity("Bangkok")
}