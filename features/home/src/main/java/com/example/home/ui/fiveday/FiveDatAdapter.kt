package com.example.home.ui.fiveday

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.utils.loadImageUrl
import com.example.domain.model.fiveday.Forecast
import com.example.home.BuildConfig
import com.example.home.R
import com.example.home.databinding.ItemFiveDayWeatherBinding
import com.example.home.utils.DateTimeUtil

class FiveDatAdapter : RecyclerView.Adapter<FiveDatAdapter.FiveDayItemViewHolder>() {

    private val weatherList: MutableList<Forecast> = mutableListOf()

    fun addList(list: List<Forecast>?) {
        list?.let {
            weatherList.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiveDayItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_five_day_weather, parent, false)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val binding = ItemFiveDayWeatherBinding.bind(view)
        return FiveDayItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FiveDayItemViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount(): Int = weatherList.size

    inner class FiveDayItemViewHolder(private val binding: ItemFiveDayWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Forecast) {
            binding.textWeatherTime.text = DateTimeUtil.convertMillisecondToDate(data.dt)
            binding.textDegree.text =
                binding.textDegree.context.getString(R.string.home_degree_with_c, data.main?.temp)
            binding.textFeelCloud.text = data.weather?.first()?.description
            binding.imageWeather.loadImageUrl(BuildConfig.IMAGE_URL + "/${data.weather?.first()?.icon}")
        }
    }
}