package com.example.weatherApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.weatherApp.model.navigation.NavGraph
import com.example.weatherApp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
//                        ActionBar(scope = scope, scaffoldState = scaffoldState, title = "Idea Bank")
                    },
                    bottomBar = {
//                        IdeaBankBottomNav(navController = navController)
                    },
                ) { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        NavGraph(navHostController = navController)
                    }
                }
            }
        }
    }
}