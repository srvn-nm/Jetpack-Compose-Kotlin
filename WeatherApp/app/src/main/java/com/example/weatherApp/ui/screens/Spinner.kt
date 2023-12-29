package com.example.weatherApp.ui.screens

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.weatherApp.jsonHandler.IranStates
import com.example.weatherApp.model.screen.WeatherScreens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownStatesMenuBox(
    options: List<IranStates>,
    navController: NavHostController,
    ) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
        expanded = it
    }) {
        TextField(
            value = selectedOption.name,
            onValueChange = {
            },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }) {
            options.forEach {
                var cities by remember { mutableStateOf(false) }
                DropdownMenuItem(
                    text = { Text(text = it.name)},
                    onClick = {
                        selectedOption = it
                        expanded = false
                        cities = true
                    }
                )
                if (cities){
                    DropdownCitiesMenuBox(options = it.cities, navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownCitiesMenuBox(options: List<IranStates.IranCity>, navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
        expanded = it
    }) {
        TextField(
            value = selectedOption.name,
            onValueChange = {
            },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(text = it.name)},
                    onClick = {
                        selectedOption = it
                        expanded = false
                        navController.navigate(WeatherScreens.Name.passInfo(it))
                    }
                )
            }
        }
    }
}
