package com.example.tourism.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tourism.ui.theme.md_theme_light_inversePrimary

@Composable
fun AttractionCard(attraction: Attraction) {
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(20.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .border(0.25.dp, color = MaterialTheme.colorScheme.inversePrimary, shape = RoundedCornerShape(0.5.dp))
                .wrapContentSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .wrapContentHeight()

            ) {
                Image(
                    painter = painterResource(id = attraction.image),
                    contentDescription = "Attraction image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                        .shadow(5.dp)
                )
                Text(
                    text = "${attraction.name}\n",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(start = 5.dp, top = 40.dp)
                        .fillMaxHeight()

                )
            }
            Text(
                text = attraction.description,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxHeight()
            )

        }
    }
}