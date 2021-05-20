package com.example.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import com.bumptech.glide.Glide
import com.example.core.BaseFragment
import com.example.home.databinding.FragmentTodayWeatherBinding
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

@FlowPreview
class TodayWeatherFragment : BaseFragment<FragmentTodayWeatherBinding>() {

    private val viewModel: HomeViewModel by sharedViewModel()
    override fun setupBinding(): FragmentTodayWeatherBinding =
        FragmentTodayWeatherBinding.inflate(layoutInflater)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.weatherLiveData.observerDataEntity(viewLifecycleOwner) { weather ->
            weather?.let {
                binding.textTitleCityName.text = it.name
                binding.textDegree.text =  it.main?.temp?.toString()
                binding.textCityName.text = getString(R.string.home_weather_in_with_text, it.name)
                binding.textDate.text = convertMillisecondToDate(it.dt)
                binding.textWind.text = getString(R.string.home_speed_and_deg_with_text, it.wind?.speed?.toInt(), it.wind?.deg)
                binding.textPerssure.text = getString(R.string.home_hpa_with_text, it.main?.pressure)
                binding.textSunrice.text = convertMillisecondToDate(it.sys?.sunrise)
                binding.textSunset.text = convertMillisecondToDate(it.sys?.sunset)
                binding.textLocationLatLon.text = "[${it.coord?.lat} , ${it.coord?.lon}]"
                binding.textHumidity.text = "${it.main?.humidity} %"
                Glide.with(this).load(BuildConfig.IMAGE_URL+"/${it.weather?.first()?.icon}").into(binding.imgWeather)
            }
        }
    }

    private fun convertMillisecondToDate(dateTime: Int?): String {
        dateTime?.let {
            val date = Calendar.getInstance().apply { timeInMillis = checkMillisecond(it) }
            val format = DateFormat.format("EEE dd MMM HH mm", date)
            return format.toString()
        }

        return ""
    }

    private fun checkMillisecond(millisecond: Int): Long {
        val s = StringBuilder(millisecond.toString())
        while (s.length < 13) {
            s.append(0)
        }

        return s.toString().toLong()
    }
}