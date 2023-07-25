package com.example.notifications_in_android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.notifications_in_android.ui.screen.DetailsScreen
import com.example.notifications_in_android.ui.screen.MainScreen

const val MY_URI = "https://stevdza-san.com"
const val MY_ARG = "message"

@Composable
fun SetUpNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Main.route
    ) {
        composable(route = Screen.Main.route) {
            MainScreen(navController = navHostController)
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument(MY_ARG) {
                    type = NavType.StringType
                }),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "$MY_URI/$MY_ARG={$MY_ARG}"
                })
        ) {
            it.arguments?.getString(MY_ARG)?.let { message ->
                DetailsScreen(message = message, navController = navHostController)
            }
        }
    }
}