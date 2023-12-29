package com.example.weatherApp.jsonHandler

data class IranStates(
    val name: String,
    val cities: List<IranCity>
) {
    data class IranCity(
        val name: String,
        val latitude: Double,
        val longitude: Double
    )
}