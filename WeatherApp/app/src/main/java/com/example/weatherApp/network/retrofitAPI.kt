package com.example.weatherApp.network

import com.example.weatherApp.network.response.WeatherInfo
import retrofit2.http.GET

interface WeatherApiService {

    @GET
    suspend fun weatherPage(): WeatherInfo
}