package com.example.notificationInAndroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.notificationInAndroid.ui.screen.MainScreen
import com.example.notificationInAndroid.ui.theme.NotificationInANdroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotificationInANdroidTheme {
                MainScreen()
            }
        }
    }
}