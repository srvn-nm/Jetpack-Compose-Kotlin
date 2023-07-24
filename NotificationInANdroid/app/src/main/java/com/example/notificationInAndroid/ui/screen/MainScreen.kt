package com.example.notificationInAndroid.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notificationInAndroid.viewModel.MainViewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    val currentContext = LocalContext.current
    DisposableEffect(currentContext) {
        mainViewModel.context = currentContext
        onDispose {
            // Clean up if needed
        }
    }
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
    }
}