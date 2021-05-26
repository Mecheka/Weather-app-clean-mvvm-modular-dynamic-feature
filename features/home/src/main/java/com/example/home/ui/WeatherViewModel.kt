package com.example.home.ui

import android.location.Address
import androidx.lifecycle.*
import com.example.core.common.DataEntity
import com.example.core.utils.toDataEntityError
import com.example.domain.model.fiveday.FiveDayOpenWeather
import com.example.domain.model.today.ToDayOpenWeather
import com.example.domain.usecase.GetFiveDayWeatherByLocationUseCase
import com.example.domain.usecase.GetToDayWeatherByLocationUseCase
import com.example.domain.usecase.GetWeatherByCityNameUseCase
import com.example.domain.usecase.WeatherBody
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
class WeatherViewModel(
    private val getToDayUseCase: GetToDayWeatherByLocationUseCase,
    private val getFiveDayUseCase: GetFiveDayWeatherByLocationUseCase,
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase
) : ViewModel() {

    private val _toDayWeatherLiveData = MutableLiveData<DataEntity<ToDayOpenWeather>>()
    val toDayWeatherLiveData: LiveData<DataEntity<ToDayOpenWeather>>
        get() = _toDayWeatherLiveData
    private val _fiveDayWeatherLiveData = MutableLiveData<DataEntity<FiveDayOpenWeather>>()
    val fiveDayWeatherLiveData: LiveData<DataEntity<FiveDayOpenWeather>>
        get() = _fiveDayWeatherLiveData
    private val _cityListLiveData = MutableLiveData<List<String>>()
    val cityListLiveData: LiveData<List<String>>
        get() = _cityListLiveData
    private val _weatherByCityLiveData = MutableLiveData<DataEntity<ToDayOpenWeather>>()
    val weatherByCityLiveData: LiveData<DataEntity<ToDayOpenWeather>>
        get() = _weatherByCityLiveData

    private var currentLocation: Address? = null

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

    fun setupCityList(json: String) {
        val sType = object : TypeToken<List<String>>() {}.type
        val gson = Gson().fromJson<List<String>>(json, sType)
        _cityListLiveData.value = gson
    }

    fun searchWeatherByCityName(search: String = "") {
        viewModelScope.launch {
            if (search.isNotBlank()) {
                getWeatherByCityNameUseCase.execute(search)
                    .catch { _weatherByCityLiveData.value = it.toDataEntityError() }
                    .collect { _weatherByCityLiveData.value = it }
            }else {
                _weatherByCityLiveData.value = _toDayWeatherLiveData.value
            }
        }
    }
}