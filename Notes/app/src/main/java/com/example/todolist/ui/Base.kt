package com.example.todolist.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

@Composable
fun Base() {
    val coroutineScope = rememberCoroutineScope()
    val job = rememberSaveable { Job() }

    DisposableEffect(key1 = job) {
        onDispose {
            job.cancel()
        }
    }

    // Your Compose UI code here
}