package com.example.notifications_in_android.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import com.example.notifications_in_android.di.RESULT_KEY
import com.example.notifications_in_android.di.ThirdNotificationCompatBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyReceiver @Inject constructor(
    @ThirdNotificationCompatBuilder
    private val notificationBuilder: NotificationCompat.Builder,
    private val notificationManager: NotificationManagerCompat
) : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            //for direct reply
            val remoteInput = intent?.let { RemoteInput.getResultsFromIntent(it) }
            if (remoteInput != null) {
                val input = remoteInput.getCharSequence(RESULT_KEY).toString()
                val person = Person.Builder().setName("Me").build()
                val message2 = NotificationCompat.MessagingStyle.Message(
                    input, System.currentTimeMillis(), person
                )
                val notificationStyle =
                    NotificationCompat.MessagingStyle(person).addMessage(message2)
                notificationManager.notify(
                    1,
                    notificationBuilder
                        .setStyle(notificationStyle)
                        .setContentTitle("Sent!")
//                    .setStyle(null)
                        .build()
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}