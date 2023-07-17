package com.example.tablayout_paging

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tablayout_paging.ui.theme.TabLayout_PagingTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabLayout_PagingTheme {
                TabScreen()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabScreen() {
    val pagerState = rememberPagerState(3)
    Column(
        modifier = Modifier
            .background(Color.LightGray)
    ) {
        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabsContent(pagerState: PagerState) {
    HorizontalPager(pageCount = 3, state = pagerState) { page ->
        when (page) {
            0 -> TabScreen1(data = "Sarvin 1, Sarvin 2, Sarvin dande be dande! Sarvin boshqab parande! Sarvin chera nemikhande?")
            1 -> TabScreen2(data = "Meow ^-^")
            2 -> TabScreen3(data = "Shibuuuuukh")
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf("Abtin", "Sarvin", "Nazanin")
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier.background(Color.Black),
        contentColor = Color.Green,
        divider = {
            Divider(
                thickness = 2.dp,
                color = Color.Green
            )
        },
        indicator = { tabPositions ->
            if (tabPositions.isNotEmpty()) {
                val selectedTabPosition = tabPositions[pagerState.currentPage]
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .offset(x = selectedTabPosition.left,y = selectedTabPosition.right)
                        .width(4.dp)
                        .height(4.dp),
                    color = Color.LightGray,
                )
            }
        }
    ) {
        list.forEachIndexed { index, name ->
            Tab(
                text = { Text(name, color = Color.Green)},
                selected = (index == pagerState.currentPage),
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(list.indexOf(name))
                    }
                },
                modifier = Modifier
                    .background(if (pagerState.currentPage == index) Color.Gray else Color.Black)
            )
        }
    }
}