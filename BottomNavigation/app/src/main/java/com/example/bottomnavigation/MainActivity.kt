package com.example.bottomnavigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bottomnavigation.navigation.BottomScreen
import com.example.bottomnavigation.navigation.bottomNavigationItems
import com.example.bottomnavigation.ui.theme.BottomNavigationTheme
import com.example.bottomnavigation.ui.theme.Purple80

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomNavigationTheme {
                BottomNavigationView()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationView() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Meow ^-^",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize()
                    )
                },
                modifier = Modifier.background(Purple80),

                )
        },
        bottomBar = {
            AppBottomNavigation(navController, bottomNavigationItems)
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = BottomScreen.Home.route,
            Modifier.padding(it)
        ) {
            composable(BottomScreen.Home.route) {

            }
            composable(BottomScreen.Favourite.route) {

            }
            composable(BottomScreen.Settings.route) {

            }
            composable(BottomScreen.Search.route) {

            }
            composable(BottomScreen.User.route) {

            }
        }
    }
}

@Composable
fun AppBottomNavigation(
    navController: NavHostController,
    bottomNavigationItems: List<BottomScreen>
) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        bottomNavigationItems.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(screen.icon, "Item icon")
                },
                label = {
                    Text(text = screen.route)
                },
                selected = false,
                alwaysShowLabel = false,
                onClick = {
                    when (screen.route) {
                        "Home" -> navController.navigate(BottomScreen.Home.route)
                        "Favourite" -> navController.navigate(BottomScreen.Favourite.route)
                        "Search" -> navController.navigate(BottomScreen.Search.route)
                        "User" -> navController.navigate(BottomScreen.User.route)
                        "Settings" -> navController.navigate(BottomScreen.Settings.route)
                    }
                }
            )
        }
    }
}
