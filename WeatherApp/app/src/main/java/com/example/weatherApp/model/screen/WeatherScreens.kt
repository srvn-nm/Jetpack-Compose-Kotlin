package com.example.weatherApp.model.screen

import com.example.weatherApp.jsonHandler.IranStates
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.net.URLEncoder

sealed class WeatherScreens(val route: String) {
    data object MainMenu : WeatherScreens(route = "first_page")

    data object Name : WeatherScreens(route = "weather_by_name/{city}") {
        fun passInfo(city: IranStates.IranCity): String {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val jsonAdapter = moshi.adapter(IranStates.IranCity::class.java).lenient()
            val cityJson = jsonAdapter.toJson(city)

            return "weather_by_name/${cityJson}"
        }
    }

    data object GPS : WeatherScreens(route = "weather_by_gps")
}