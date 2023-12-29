package com.example.weatherApp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.weatherApp.R
import com.example.weatherApp.jsonHandler.Iran
import com.example.weatherApp.jsonHandler.readJSONFromAssets
import com.example.weatherApp.model.screen.WeatherScreens
import com.google.gson.Gson

@Composable
fun FirstPage(navController: NavHostController) {
    val iranData = Gson().fromJson(
        readJSONFromAssets(
            LocalContext.current,
            "iran_cities_with_coordinates.json"
        ), Iran::class.java
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            var nameShow by remember {
                mutableStateOf(false)
            }
            Button(
                onClick = { nameShow = !nameShow },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(61.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.DarkGray),
            ) {
                Text(text = "Weather Condition and Air Pollution by Choosing")
            }
            if (nameShow) {
                Image(
                    painter = painterResource(id = R.drawable.images),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Text("Please choose city name from below.")
                DropdownStatesMenuBox(iranData.states, navController)
            }
            if (!nameShow) {
                Button(
                    onClick = { navController.navigate(WeatherScreens.GPS.route) },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(61.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.DarkGray),
                ) {
                    Text(text = "Weather Condition and Air Pollution using GPS")
                }
            }
        }
    }
}