package com.example.home

import android.location.Address
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.DataEntity
import com.example.core.utils.toDataEntityError
import com.example.domain.model.OpenWeather
import com.example.domain.usecase.GetWeatherByCityUseCase
import com.example.domain.usecase.WeatherBody
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
class HomeViewModel(private val useCase: GetWeatherByCityUseCase) : ViewModel() {

    private val _weatherLiveDate = MutableLiveData<DataEntity<OpenWeather>>()
    val weatherLiveData: LiveData<DataEntity<OpenWeather>>
        get() = _weatherLiveDate

    var currentLocation: Address? = null

    fun getWeather(address: Address) {
        viewModelScope.launch {
            currentLocation = address
            useCase.execute(WeatherBody(address.latitude, address.longitude))
                .catch { _weatherLiveDate.value = it.toDataEntityError() }
                .collect {
                _weatherLiveDate.value = it
            }
        }
    }
}