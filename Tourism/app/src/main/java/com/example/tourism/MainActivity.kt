package com.example.tourism

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.tourism.model.AttractionCard
import com.example.tourism.model.AttractionItems
import com.example.tourism.ui.theme.TourismTheme
import com.example.tourism.ui.utils.KhashAppBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TourismTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = { KhashAppBar() }
                    ) {
                        LazyColumn(contentPadding = it) {
                            val khashAttractions = listOf(
                                AttractionItems.ZarrinSanganCypress,
                                AttractionItems.heidarAbadCastle,
                                AttractionItems.irendganCastle,
                                AttractionItems.threeSeeLake,
                                AttractionItems.taftanMountains
                            )
                            items(khashAttractions) {attraction->
                                AttractionCard(attraction = attraction)
                            }
                        }
                    }
                }
            }
        }
    }
}