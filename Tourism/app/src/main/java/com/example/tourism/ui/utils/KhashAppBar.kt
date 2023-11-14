package com.example.tourism.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tourism.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KhashAppBar() {
    CenterAlignedTopAppBar(title = {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.cypress_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .padding(2.dp)
                    .clip(CircleShape)
            )
            Text(
                text = stringResource(R.string.app_name_persian),
                style = MaterialTheme.typography.titleLarge)
        }
    })
}