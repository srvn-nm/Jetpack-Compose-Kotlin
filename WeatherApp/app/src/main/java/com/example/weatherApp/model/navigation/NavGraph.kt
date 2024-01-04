package com.example.weatherApp.model.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherApp.jsonHandler.IranStates
import com.example.weatherApp.model.screen.WeatherScreens
import com.example.weatherApp.ui.screens.FirstPage
import com.example.weatherApp.ui.screens.WeatherByGPSScreen
import com.example.weatherApp.ui.screens.WeatherByNameScreen
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,

        startDestination = WeatherScreens.MainMenu.route
    ) {

        composable(route = WeatherScreens.MainMenu.route) {
            FirstPage(navController = navHostController)
        }

        composable(
            route = WeatherScreens.Name.route,
        ) { backStackEntry ->
            val cityJson = backStackEntry.arguments?.getString("city")
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val jsonAdapter = moshi.adapter(IranStates.IranCity::class.java).lenient()
            val city = cityJson?.let { jsonAdapter.fromJson(it) }
            if (city != null) {
                WeatherByNameScreen(
                    city = city
                )
            }
        }

        composable(route = WeatherScreens.GPS.route) {
            WeatherByGPSScreen()
        }
    }
}