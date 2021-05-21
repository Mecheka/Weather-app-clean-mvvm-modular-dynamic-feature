package com.example.domain.di

import com.example.domain.usecase.GetFiveDayWeatherByLocationUseCase
import com.example.domain.usecase.GetToDayWeatherByLocationUseCase
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module

@FlowPreview
val domainModule = module {

    factory { GetToDayWeatherByLocationUseCase(get()) }
    factory { GetFiveDayWeatherByLocationUseCase(get()) }
}