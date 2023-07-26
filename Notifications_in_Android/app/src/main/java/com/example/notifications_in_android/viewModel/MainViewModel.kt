package com.example.notifications_in_android.viewModel

import android.annotation.SuppressLint
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notifications_in_android.di.MainNotificationCompatBuilder
import com.example.notifications_in_android.di.SecondNotificationCompatBuilder
import com.example.notifications_in_android.di.ThirdNotificationCompatBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @MainNotificationCompatBuilder
    private val notificationBuilder: NotificationCompat.Builder,
    @SecondNotificationCompatBuilder
    private val notificationBuilder2: NotificationCompat.Builder,
    @ThirdNotificationCompatBuilder
    private val notificationBuilder3: NotificationCompat.Builder,
    private val notificationManager: NotificationManagerCompat
) : ViewModel() {


    @SuppressLint("MissingPermission")
    fun showSimpleNotification() {
        notificationManager.notify(1, notificationBuilder.build())
        notificationManager.notify(2, notificationBuilder3.build())
    }

    @SuppressLint("MissingPermission")
    fun updateSimpleNotification() {
        notificationManager.notify(
            1, notificationBuilder
                .setContentTitle("NEW TITLE")
                .build()
        )
        notificationManager.notify(
            2, notificationBuilder3
                .setContentTitle("NEW TITLE")
                .build()
        )
    }

    fun cancelSimpleNotification() {
        notificationManager.cancel(1)
        notificationManager.cancel(2)
    }

    @SuppressLint("MissingPermission")
    fun showProgress() {
        val max = 10
        var progress = 0
        viewModelScope.launch {
            while (progress != max) {
                delay(1000)
                progress += 1
                notificationManager.notify(
                    3,
                    notificationBuilder2
                        .setContentTitle("Downloading")
                        .setContentText("${progress}/${max}")
                        .setProgress(max, progress, false).build()
                )
            }
            //for ending download
            notificationManager.notify(
                3,
                //contains sound and higher priority
                notificationBuilder
                    .setContentTitle("Completed!")
                    .setContentText("")
                    //the notification is not clickable anymore
                    .setContentIntent(null)
                    //delete action buttons
                    .clearActions()
                    .setProgress(0, 0, false).build()
            )
        }
    }
    @SuppressLint("MissingPermission")
    fun replyButton(){
        notificationManager.notify(1, notificationBuilder.build())
    }


}