package com.example.home.ui.fiveday

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.core.BaseFragment
import com.example.home.R
import com.example.home.databinding.FragmentFiveDayWeatherBinding
import com.example.home.ui.WeatherViewModel
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@FlowPreview
class FiveDayWeatherFragment : BaseFragment<FragmentFiveDayWeatherBinding>() {

    private val viewModel: WeatherViewModel by sharedViewModel()
    private val weatherAdapter: FiveDatAdapter by lazy { FiveDatAdapter() }

    override fun setupBinding(): FragmentFiveDayWeatherBinding =
        FragmentFiveDayWeatherBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        observer()
    }

    private fun setupView() {

        val snapHelper = LinearSnapHelper()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = weatherAdapter
        }

        snapHelper.attachToRecyclerView(binding.recyclerView)
    }

    @SuppressLint("SetTextI18n")
    private fun observer() {
        viewModel.fiveDayWeatherLiveData.observerDataEntity(viewLifecycleOwner) {
            binding.textLocationLatLon.text = "[${it?.city?.coord?.lat} , ${it?.city?.coord?.lon}]"
            weatherAdapter.addList(it?.list)
        }

        viewModel.toDayWeatherLiveData.observerDataEntity(viewLifecycleOwner) {
            binding.textWeatherInCityName.text = it?.name
        }
    }
}