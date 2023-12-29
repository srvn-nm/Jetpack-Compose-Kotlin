package com.example.weatherApp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.weatherApp.R
import com.example.weatherApp.jsonHandler.IranStates
import com.example.weatherApp.viewModel.RequestViewModel
import kotlin.math.sqrt

@Composable
fun WeatherByNameScreen(navController: NavHostController, city: IranStates.IranCity?) {
    val viewModel = viewModel(modelClass = RequestViewModel::class.java)
    if (city != null) {
        viewModel.cityWeatherApi(cityName = city.name)
    }
    val cityWeather = viewModel.weatherInformation.collectAsState()
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

                if (city != null) {
                    Text(
                        text = city.name,
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
                    CardViewContent("Sunrise", "ic_sunrise2")
                    Spacer(modifier = Modifier.height(16.dp))
                    CardViewContent("Temp", "ic_celcius")
                    Spacer(modifier = Modifier.height(16.dp))
                    CardViewContent("Temp Min", "ic_temperature_down")
                    Spacer(modifier = Modifier.height(16.dp))
                    CardViewContent("Pressure", "ic_temperature")
                }
            }
        }
    }
}

@Composable
fun CardViewContent(text1: String, imageRes1: String, text2:String, imageRes2: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .padding(10.dp)
            .background(Color(0xFFECF5F6))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = text1,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = text2,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_sunrise2), // Replace with your drawable resource
                    contentDescription = "Sunrise Icon",
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_sunrise2), // Replace with your drawable resource
                    contentDescription = "Sunrise Icon",
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun PollutionShow(x: Double, y: Double) {
    val viewModel = viewModel(modelClass = RequestViewModel::class.java)
    viewModel.pollutionInformation()
    val pollution = viewModel.pollutionInformation.collectAsState()
    var minDistance: Double? = null
    var rank: Int? = null
    pollution.value.forEach {
        val distance = calculateDistance(Pair(x, y), Pair(it.Latitude!!, it.Longitude!!))
        if (minDistance == null || distance < minDistance!!) {
            minDistance = distance
            rank = it.AQI
        }
    }
    val color = when (rank) {
        in 0..50 -> Color(0,228,0)
        in 51..100 -> Color(255,255,0)
        in 101..150 -> Color(255,126,0)
        in 151..200 -> Color(255,0,0)
        in 201..300 -> Color(153,0,76)
        in 301..500 -> Color(76,0,38)
        else -> Color.Black
    }
}

fun calculateDistance(dot1: Pair<Double, Double>, dot2: Pair<Double, Double>): Double {
    val deltaX = dot2.first - dot1.first
    val deltaY = dot2.second - dot1.second
    return sqrt(deltaX * deltaX + deltaY * deltaY)
}
