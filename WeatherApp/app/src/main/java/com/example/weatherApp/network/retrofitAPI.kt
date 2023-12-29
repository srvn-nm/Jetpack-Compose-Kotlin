package com.example.weatherApp.network

import com.example.weatherApp.network.response.PollutionInfo
import com.example.weatherApp.network.response.WeatherInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApiService {

    @GET("forecast?")
    suspend fun weatherPage(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String
    ): WeatherInfo

    @GET("LoadAQIMap?id=1")
    suspend fun pollutionInfo(): List<PollutionInfo>
}