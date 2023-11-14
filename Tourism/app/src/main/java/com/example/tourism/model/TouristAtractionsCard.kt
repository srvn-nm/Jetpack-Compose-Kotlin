package com.example.tourism.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AttractionCard(attraction: Attraction) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth(),
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .border(0.25.dp, color = Color.Gray, shape = CircleShape)
                .padding(10.dp)
                .wrapContentHeight()
        ) {
            Image(
                painter = painterResource(id = attraction.image),
                contentDescription = "Attraction image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
            Column {
                Text(
                    text = "${attraction.name}\n",
                    color = Color.DarkGray,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 5.dp, top = 20.dp)
                        .fillMaxHeight()
                )
                Text(
                    text = attraction.description,
                    color = Color.DarkGray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(start = 5.dp, top = 20.dp)
                        .fillMaxHeight()
                )
            }
        }
    }
}