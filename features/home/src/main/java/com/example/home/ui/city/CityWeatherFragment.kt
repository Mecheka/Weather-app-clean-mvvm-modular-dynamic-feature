package com.example.home.ui.city

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.core.BaseFragment
import com.example.core.utils.loadImageUrl
import com.example.home.BuildConfig
import com.example.home.R
import com.example.home.databinding.FragmentCityWeatherBinding
import com.example.home.ui.WeatherViewModel
import com.example.home.utils.DateTimeUtil
import com.example.home.utils.doOnAfterTextChange
import com.example.home.utils.doOnConfirm
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@FlowPreview
class CityWeatherFragment : BaseFragment<FragmentCityWeatherBinding>() {

    private val viewModel: WeatherViewModel by sharedViewModel()

    override fun setupBinding(): FragmentCityWeatherBinding =
        FragmentCityWeatherBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.searchWeatherByCityName()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
    }

    @SuppressLint("SetTextI18n")
    private fun observer() {
        viewModel.cityListLiveData.observe(viewLifecycleOwner) { city ->

            with(binding.searchView) {

                doOnAfterTextChange { text ->
                    val query = city.filter { it.contains(text) }
                    if (binding.searchView.lastSuggestions.isNotEmpty()) {
                        binding.searchView.lastSuggestions = query
                    } else {
                        binding.searchView.updateLastSuggestions(query)
                    }
                }

                doOnConfirm {
                    Log.d("Confirm search ==>", "search $it")
                    viewModel.searchWeatherByCityName(it)
                }
            }
        }

        viewModel.weatherByCityLiveData.observerDataEntity(viewLifecycleOwner) { weather ->
            weather?.let {
                binding.textTitleCityName.text = it.name
                binding.textDegree.text = it.main?.temp?.toString()
                binding.textCityName.text = getString(R.string.home_weather_in_with_text, it.name)
                binding.textDate.text = DateTimeUtil.convertMillisecondToDate(it.dt)
                binding.textWind.text = getString(
                    R.string.home_speed_and_deg_with_text,
                    it.wind?.speed?.toInt(),
                    it.wind?.deg
                )
                binding.textPerssure.text =
                    getString(R.string.home_hpa_with_text, it.main?.pressure)
                binding.textSunrice.text = DateTimeUtil.convertMillisecondToDate(it.sys?.sunrise)
                binding.textSunset.text = DateTimeUtil.convertMillisecondToDate(it.sys?.sunset)
                binding.textLocationLatLon.text = "[${it.coord?.lat} , ${it.coord?.lon}]"
                binding.textHumidity.text = "${it.main?.humidity} %"
                binding.imgWeather.loadImageUrl(BuildConfig.IMAGE_URL + "/${it.weather?.first()?.icon}")
            }
        }
    }
}