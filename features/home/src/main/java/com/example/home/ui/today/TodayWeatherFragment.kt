package com.example.home.ui.today

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.core.BaseFragment
import com.example.core.utils.loadImageUrl
import com.example.home.BuildConfig
import com.example.home.R
import com.example.home.databinding.FragmentTodayWeatherBinding
import com.example.home.ui.WeatherViewModel
import com.example.home.utils.DateTimeUtil.Companion.convertMillisecondToDate
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@FlowPreview
class TodayWeatherFragment : BaseFragment<FragmentTodayWeatherBinding>() {

    private val viewModel: WeatherViewModel by sharedViewModel()
    override fun setupBinding(): FragmentTodayWeatherBinding =
        FragmentTodayWeatherBinding.inflate(layoutInflater)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.toDayWeatherLiveData.observerDataEntity(viewLifecycleOwner) { weather ->
            weather?.let {
                binding.textTitleCityName.text = it.name
                binding.textDegree.text = it.main?.temp?.toString()
                binding.textCityName.text = getString(R.string.home_weather_in_with_text, it.name)
                binding.textDate.text = convertMillisecondToDate(it.dt)
                binding.textWind.text = getString(
                    R.string.home_speed_and_deg_with_text,
                    it.wind?.speed?.toInt(),
                    it.wind?.deg
                )
                binding.textPerssure.text =
                    getString(R.string.home_hpa_with_text, it.main?.pressure)
                binding.textSunrice.text = convertMillisecondToDate(it.sys?.sunrise)
                binding.textSunset.text = convertMillisecondToDate(it.sys?.sunset)
                binding.textLocationLatLon.text = "[${it.coord?.lat} , ${it.coord?.lon}]"
                binding.textHumidity.text = "${it.main?.humidity} %"
                binding.imgWeather.loadImageUrl(BuildConfig.IMAGE_URL + "/${it.weather?.first()?.icon}")
            }
        }
    }
}