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

var replyMessage = ""

@AndroidEntryPoint
class MyReceiver : BroadcastReceiver() {

    @ThirdNotificationCompatBuilder
    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var notificationManager: NotificationManagerCompat

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            //for direct reply
            val remoteInput = intent?.let { RemoteInput.getResultsFromIntent(it) }
            if (remoteInput != null) {
                val input = remoteInput.getCharSequence(RESULT_KEY).toString()
                val person = Person.Builder().setName("Me").build()
                val message = NotificationCompat.MessagingStyle.Message(
                    input, System.currentTimeMillis(), person
                )
                val notificationStyle =
                    NotificationCompat.MessagingStyle(person).addMessage(message)

                //the reply message
                replyMessage += "\n" + RemoteInput.getResultsFromIntent(intent)
                    ?.getCharSequence(RESULT_KEY).toString()

                notificationManager.notify(
                    2,
                    notificationBuilder
                        .setStyle(notificationStyle)
                        .setContentTitle("$replyMessage Sent!")
//                        .setStyle(null)
                        .build()
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}