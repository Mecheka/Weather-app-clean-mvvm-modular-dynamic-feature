package com.example.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    fun create(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient())
        .build()

    private fun httpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        val clientBuilder = OkHttpClient.Builder()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(httpLoggingInterceptor)
        clientBuilder.readTimeout(120, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(120, TimeUnit.SECONDS)
        return clientBuilder.build()
    }
}