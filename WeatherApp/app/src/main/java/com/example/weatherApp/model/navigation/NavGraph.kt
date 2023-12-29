package com.example.weatherApp.model.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.weatherApp.model.screen.WeatherScreens
import com.example.weatherApp.ui.screens.FirstPage
import com.example.weatherApp.ui.screens.WeatherByGPSScreen
import com.example.weatherApp.ui.screens.WeatherByNameScreen

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
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                }
            )
        ) {
            WeatherByNameScreen(
                navController = navHostController,
                name = it.arguments!!.getString("name")!!
            )
        }

        composable(route = WeatherScreens.GPS.route) {
            WeatherByGPSScreen(navController = navHostController)
        }
    }
}