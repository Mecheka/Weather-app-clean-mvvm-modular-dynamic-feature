package com.example.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetWeatherByCityUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
class HomeViewModel(private val useCase: GetWeatherByCityUseCase): ViewModel() {

    fun getWeather() {
        viewModelScope.launch {
            useCase.execute(Unit).collect {
                Log.d("Debug log ===>", "Weather")
            }
        }
    }
}