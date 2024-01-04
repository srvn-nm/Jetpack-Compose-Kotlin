package com.example.weatherApp.ui.screens

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherApp.R
import com.example.weatherApp.location.LocationViewModel
import com.example.weatherApp.viewModel.RequestViewModel

@Composable
fun WeatherByGPSScreen() {
    val context = LocalContext.current
    val viewModel = viewModel {
        LocationViewModel(context.applicationContext as Application)
    }
    val location by viewModel.getLocationLiveData().observeAsState()

    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted){
            viewModel.startLocationUpdates()
        }else{
            Toast.makeText(context, "GPS Unavailable >~<", Toast.LENGTH_LONG).show()
        }
    }

    if(permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PERMISSION_GRANTED
        }){
        viewModel.startLocationUpdates()
println(location)
        val viewModel2 = viewModel(modelClass = RequestViewModel::class.java)
        if (location != null) {
            viewModel2.cityWeatherApi(location!!.latitude, location!!.longitude)
        }
        val cityWeather = viewModel2.weatherInformation.collectAsState()
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

                    if (location != null) {
                        Text(
                            text = "Your location!",
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
                        PollutionShow(location!!.latitude, location!!.longitude)
                        CardViewContent("Sunrise: ${cityWeather.value?.city?.sunrise}", R.drawable.ic_sunrise2, "Sunset: ${cityWeather.value?.city?.sunset}", R.drawable.ic_sunset2)
                        Spacer(modifier = Modifier.height(16.dp))
                        cityWeather.value?.list?.forEach {
                            Text(text = it.dt_txt)
                            CardViewContent("Temp: ${it.main.temp}", R.drawable.ic_celcius, "Feels Like: ${it.main.feels_like}", R.drawable.ic_temperature)
                            Spacer(modifier = Modifier.height(16.dp))
                            CardViewContent("Temp Min: ${it.main.temp_min}", R.drawable.ic_temperature_down, "Temp Max: ${it.main.temp_max}", R.drawable.ic_temperature_up)
                            Spacer(modifier = Modifier.height(16.dp))
                            CardViewContent("Pressure: ${it.main.pressure}", R.drawable.ic_temperature,"Humidity: ${it.main.humidity}", R.drawable.ic_humidity)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }

    }else {
        SideEffect {
            launcher.launch(permissions)
        }
    }
}