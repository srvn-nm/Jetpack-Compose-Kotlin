package com.example.weatherApp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherApp.network.WeatherApiService
import com.example.weatherApp.network.response.PollutionInfo
import com.example.weatherApp.network.response.WeatherInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RequestViewModel : ViewModel() {

    private val _weatherInformation = MutableStateFlow<WeatherInfo?>(null)
    val weatherInformation: StateFlow<WeatherInfo?> get() = _weatherInformation

    private val _pollutionInformation = MutableStateFlow(emptyList<PollutionInfo>())
    val pollutionInformation: StateFlow<List<PollutionInfo>> get() = _pollutionInformation

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                .method(originalRequest.method, originalRequest.body)
            chain.proceed(requestBuilder.build())
        }.build()


    fun cityWeatherApi(cityName: String) {
        viewModelScope.launch {
            try {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/data/2.5/weather?q=${cityName}&appid=501b25848afaeca041fb1ce35525d09b&units=metric")
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .client(okHttpClient)
                    .build()
                    .create(WeatherApiService::class.java)

                _weatherInformation.value = retrofit.weatherPage()

            } catch (exception: Exception) {
                exception.printStackTrace()
                if (exception.message.toString().contains("50")) {
                    println("server time out")
                } else {
                    println("some things went wrong")
                }
            }
        }
    }

    fun pollutionInformation(){
        viewModelScope.launch {
            try {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://aqms.doe.ir/Home/LoadAQIMap?id=1")
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .client(okHttpClient)
                    .build()
                    .create(WeatherApiService::class.java)

                _pollutionInformation.value = retrofit.pollutionInfo()

            } catch (exception: Exception) {
                exception.printStackTrace()
                if (exception.message.toString().contains("50")) {
                    println("server time out")
                } else {
                    println("some things went wrong")
                }
            }
        }
    }
}