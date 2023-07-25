package com.example.notifications_in_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.notificationInAndroid.navigation.SetUpNavGraph
import com.example.notificationInAndroid.ui.theme.NotificationInAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotificationInAndroidTheme {
                val navController = rememberNavController()
                SetUpNavGraph(navController)
            }
        }
    }
}