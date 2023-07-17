package com.example.tablayout_paging

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TabScreen1(data: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$data\n\nin tab screen 1!",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Blue,
            fontFamily = FontFamily.Monospace,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TabScreen2(data: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$data\n\nin tab screen 2!",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Blue,
            fontFamily = FontFamily.Monospace,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TabScreen3(data: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$data\n\nin tab screen 3!",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Blue,
            fontFamily = FontFamily.Monospace,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )
    }
}