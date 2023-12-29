package com.example.weatherApp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.weatherApp.R

@Composable
fun WeatherByNameScreen(navController: NavHostController, name: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.large)
                .background(Color.White)
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.tower),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 4.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontFamily = FontFamily.Cursive,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
            }
        }
    }
}