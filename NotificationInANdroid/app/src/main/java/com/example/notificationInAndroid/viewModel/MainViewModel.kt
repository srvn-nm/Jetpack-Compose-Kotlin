package com.example.notificationInAndroid.viewModel

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import com.example.notificationInAndroid.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val notificationBuilder: NotificationCompat.Builder,
    private val notificationManager: NotificationManagerCompat,
) : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    lateinit var context :Context


    fun showSimpleNotification() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
//            val permission = Manifest.permission.ACCESS_NOTIFICATION_POLICY
//            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(MainActivity(), arrayOf(permission), 1234)
//            }
            return
        }
        notificationManager.notify(1, notificationBuilder.build())
    }

    fun updateSimpleNotification() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
//            val permission = Manifest.permission.ACCESS_NOTIFICATION_POLICY
//            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(MainActivity(), arrayOf(permission), 1234)
//            }
            return
        }
        notificationManager.notify(1, notificationBuilder
            .setContentTitle("NEW TITLE")
            .build()
        )
    }

    fun cancelSimpleNotification() {
        notificationManager.cancel(1)
    }

}
