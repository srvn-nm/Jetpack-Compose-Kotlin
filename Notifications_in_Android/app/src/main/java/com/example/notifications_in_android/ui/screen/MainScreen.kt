package com.example.notifications_in_android.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notifications_in_android.navigation.Screen
import com.example.notifications_in_android.viewModel.MainViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = mainViewModel::showSimpleNotification) {
            Text(text = "Simple Notification")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = mainViewModel::updateSimpleNotification) {
            Text(text = "Update Notification")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = mainViewModel::cancelSimpleNotification) {
            Text(text = "Cancel Notification")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            navController.navigate(
                Screen.Details.passArgument(
                    message = "Coming from Main Screen! ^-^"
                )
            )
        }) {
            Text(text = "Details Screen")
        }
    }
}