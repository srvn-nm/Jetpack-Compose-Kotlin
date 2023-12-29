package com.example.weatherApp.model.screen

sealed class WeatherScreens(val route: String) {
    data object MainMenu : WeatherScreens(route = "first_page")

    data object Name : WeatherScreens(route = "weather_by_name/{name}") {
        fun passInfo(name: String): String {
            return "weather_by_name/${name}"
        }
    }

    data object GPS : WeatherScreens(route = "weather_by_gps")
}