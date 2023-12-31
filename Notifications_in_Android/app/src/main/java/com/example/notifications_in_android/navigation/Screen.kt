package com.example.notifications_in_android.navigation

sealed class Screen(val route: String) {
    object Main : Screen(route = "main")
    object Details : Screen(route = "details/{$MY_ARG}") {
        fun passArgument(message: String) = "details/$message"
    }
}
