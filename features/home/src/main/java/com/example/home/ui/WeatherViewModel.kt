package com.example.home.ui

import android.location.Address
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.DataEntity
import com.example.core.utils.toDataEntityError
import com.example.domain.model.fiveday.FiveDayOpenWeather
import com.example.domain.model.today.ToDayOpenWeather
import com.example.domain.usecase.GetFiveDayWeatherByLocationUseCase
import com.example.domain.usecase.GetToDayWeatherByLocationUseCase
import com.example.domain.usecase.WeatherBody
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
class WeatherViewModel(
    private val getToDayUseCase: GetToDayWeatherByLocationUseCase,
    private val getFiveDayUseCase: GetFiveDayWeatherByLocationUseCase
) : ViewModel() {

    private val _toDayWeatherLiveData = MutableLiveData<DataEntity<ToDayOpenWeather>>()
    val toDayWeatherLiveData: LiveData<DataEntity<ToDayOpenWeather>>
        get() = _toDayWeatherLiveData
    private val _fiveDayWeatherLiveData = MutableLiveData<DataEntity<FiveDayOpenWeather>>()
    val fiveDayWeatherLiveData: LiveData<DataEntity<FiveDayOpenWeather>>
        get() = _fiveDayWeatherLiveData

    var currentLocation: Address? = null

    fun getWeather(address: Address) {
        viewModelScope.launch {
            currentLocation = address
            val body = WeatherBody(address.latitude, address.longitude)
            getToDayUseCase.execute(body)
                .catch { _toDayWeatherLiveData.value = it.toDataEntityError() }
                .collect {
                    _toDayWeatherLiveData.value = it
                }

            getFiveDayUseCase.execute(body)
                .catch { _fiveDayWeatherLiveData.value = it.toDataEntityError() }
                .collect {
                    _fiveDayWeatherLiveData.value = it
                }
        }
    }
}