package com.example.data.di

import com.example.data.RetrofitClient
import com.example.data.network.WeatherRepositoryImpl
import com.example.data.network.service.OpenWeatherService
import com.example.domain.repository.WeatherRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {

    single { RetrofitClient.create() }

    factory { get<Retrofit>().create(OpenWeatherService::class.java) }

    factory<WeatherRepository> { WeatherRepositoryImpl(get()) }
}