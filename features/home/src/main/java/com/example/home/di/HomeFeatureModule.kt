package com.example.home.di

import com.example.home.ui.WeatherViewModel
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@FlowPreview
val homeFeatureModule = module {

    viewModel { WeatherViewModel(get(), get()) }
}