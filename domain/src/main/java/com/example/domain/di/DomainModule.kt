package com.example.domain.di

import com.example.domain.usecase.GetFiveDayWeatherByLocationUseCase
import com.example.domain.usecase.GetToDayWeatherByLocationUseCase
import com.example.domain.usecase.GetWeatherByCityNameUseCase
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module

@FlowPreview
val domainModule = module {

    factory { GetToDayWeatherByLocationUseCase(get()) }
    factory { GetFiveDayWeatherByLocationUseCase(get()) }
    factory { GetWeatherByCityNameUseCase(get()) }
}