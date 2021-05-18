package com.example.domain.di

import com.example.domain.usecase.GetWeatherByCityUseCase
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module

@FlowPreview
val domainModule = module {

    factory { GetWeatherByCityUseCase(get()) }
}