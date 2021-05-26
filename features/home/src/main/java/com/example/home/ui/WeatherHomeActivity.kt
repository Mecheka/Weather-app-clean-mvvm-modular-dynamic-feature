@file:Suppress("BlockingMethodInNonBlockingContext")

package com.example.home.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.IntentSender
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.example.core.BaseActivity
import com.example.home.databinding.ActivityHomeBinding
import com.example.home.ui.city.CityWeatherFragment
import com.example.home.ui.fiveday.FiveDayWeatherFragment
import com.example.home.ui.today.TodayWeatherFragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.material.tabs.TabLayoutMediator
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

@FlowPreview
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class WeatherHomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private val weatherHomeFragmentAdapter: WeatherHomeCollectionAdapter by lazy {
        WeatherHomeCollectionAdapter(
            supportFragmentManager,
            lifecycle
        )
    }
    private val viewModel: WeatherViewModel by viewModel()

    private var requestLocationUpdate = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this@WeatherHomeActivity)
        setupView()
        lifecycleScope.launch {
            loadCityListAssets().flowOn(Dispatchers.IO).collect { viewModel.setupCityList(it) }
        }
    }

    override fun onStart() {
        super.onStart()

        checkLocationPermission()
    }

    override fun onStop() {
        super.onStop()

        requestLocationUpdate = true
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun setupView() {
        showLoadingDialog()
        setSupportActionBar(binding.toolBar)

        setupViewPager()
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = weatherHomeFragmentAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Today"
                1 -> "Five day"
                2 -> "City"
                else -> "Un know"
            }
        }.attach()
        weatherHomeFragmentAdapter.addFragment(TodayWeatherFragment())
        weatherHomeFragmentAdapter.addFragment(FiveDayWeatherFragment())
        weatherHomeFragmentAdapter.addFragment(CityWeatherFragment())
    }

    private suspend fun loadCityListAssets() = flow {
        val json: String
        try {
            val input = assets.open("city_list.json")
            val size = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            json = String(buffer, Charset.forName("UTF-8"))
            emit(json)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun checkLocationPermission() {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    setupLocationRequest()
                    setupLocationCallBack()
                    if (requestLocationUpdate) startLocationUpdates()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

            }).check()
    }

    @SuppressLint("MissingPermission")
    private fun setupLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder)

//        task.addOnSuccessListener { }
        task.addOnFailureListener {
            if (it is ResolvableApiException) {
                try {
                    it.startResolutionForResult(this, CHECK_LOCATION_SETTING)
                } catch (sendEx: IntentSender.SendIntentException) {

                }
            }
        }
    }

    private fun setupLocationCallBack() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                if (requestLocationUpdate) {
                    hideLoadingDialog()
                    requestLocationUpdate = false
                    getWeatherByLastLocation(locationResult.lastLocation)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getWeatherByLastLocation(lastLocation: Location? = null) {
        lastLocation?.let {
            val geocoder = Geocoder(this@WeatherHomeActivity, Locale.getDefault())
            val address = geocoder.getFromLocation(it.latitude, it.longitude, 1)
            viewModel.getWeather(address.first())
        }

    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    companion object {
        const val CHECK_LOCATION_SETTING = 101
    }
}