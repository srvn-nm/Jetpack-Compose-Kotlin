package com.example.weatherApp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
        viewModel.cityWeatherApi(city.latitude, city.longitude)
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
                    PollutionShow(city.latitude, city.longitude)
                    CardViewContent("Sunrise: ${cityWeather.value?.city?.sunrise}", R.drawable.ic_sunrise2, "Sunset: ${cityWeather.value?.city?.sunset}", R.drawable.ic_sunset2)
                    Spacer(modifier = Modifier.height(16.dp))
                    cityWeather.value?.list?.forEach {
                        CardViewContent("Temp: ${it.main.temp}", R.drawable.ic_celcius, "Feels Like: ${it.main.feels_like}", R.drawable.ic_temperature)
                        Spacer(modifier = Modifier.height(16.dp))
                        CardViewContent("Temp Min: ${it.main.temp_min}", R.drawable.ic_temperature_down, "Temp Max: ${it.main.temp_max}", R.drawable.ic_temperature_up)
                        Spacer(modifier = Modifier.height(16.dp))
                        CardViewContent("Pressure: ${it.main.pressure}", R.drawable.ic_temperature,"Humidity: ${it.main.humidity}", R.drawable.ic_humidity)
                    }
                }
            }
        }
    }
}

@Composable
fun CardViewContent(text1: String, imageRes1: Int, text2:String, imageRes2: Int) {
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
                    painter = painterResource(id = imageRes1), // Replace with your drawable resource
                    contentDescription = "Sunrise Icon",
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = imageRes2), // Replace with your drawable resource
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

    Card {
        Row {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(
                        color = color,
                        shape = CircleShape
                    )
//                    .border(1.dp, Color.Black, CircleShape)
            )
            Text(text = "Pollution AQI Rate: ${rank.toString()}")
        }
    }
}

fun calculateDistance(dot1: Pair<Double, Double>, dot2: Pair<Double, Double>): Double {
    val deltaX = dot2.first - dot1.first
    val deltaY = dot2.second - dot1.second
    return sqrt(deltaX * deltaX + deltaY * deltaY)
}
