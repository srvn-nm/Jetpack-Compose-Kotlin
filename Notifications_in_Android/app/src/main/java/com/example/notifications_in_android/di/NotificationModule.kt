package com.example.notifications_in_android.di

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PRIVATE
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import androidx.core.graphics.drawable.IconCompat
import androidx.core.net.toUri
import com.example.notifications_in_android.MainActivity
import com.example.notifications_in_android.R
import com.example.notifications_in_android.navigation.MY_ARG
import com.example.notifications_in_android.navigation.MY_URI
import com.example.notifications_in_android.receiver.MyReceiver
import com.example.notifications_in_android.receiver.MyReceiver2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

const val RESULT_KEY = "RESULT_KEY"

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @SuppressLint("ObsoleteSdkInt")
    @Singleton
    @Provides
    @MainNotificationCompatBuilder
    fun provideNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {

        val flag =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0

        val intent = Intent(context, MyReceiver2::class.java).apply {
            putExtra("MESSAGE", "Meow!")
        }

        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, flag)

        //for navigating to the details screen
        val clickIntent = Intent(
            Intent.ACTION_VIEW,
            "$MY_URI/$MY_ARG=Coming from Notification! ^-^".toUri(),
            context,
            MainActivity::class.java
        )

        val clickPendingIntent: PendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(clickIntent)
            getPendingIntent(1, flag)
        }

//        for going to an activity
//        val clickPendingIntent = PendingIntent.getActivity(context, 1, clickIntent, flag)

        return NotificationCompat.Builder(context, "Main Channel ID")
            .setContentTitle("Notification")
            .setContentText("Sarvin did this!")
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(VISIBILITY_PRIVATE)
            .setPublicVersion(
                NotificationCompat.Builder(context, "Main Channel ID")
                    .setContentTitle("Hidden")
                    .setContentText("Unlock to see the message.")
                    .build()
            )
            .addAction(R.drawable.ic_baseline_notifications_24, "ACTION", pendingIntent)
            .setContentIntent(clickPendingIntent)
    }

    @Singleton
    @Provides
    @ThirdNotificationCompatBuilder
    fun provideThirdNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        val flag =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.FLAG_MUTABLE
            } else
                0

        //direct reply code
        val remoteInput = RemoteInput.Builder(RESULT_KEY)
            .setLabel("Type here...")
            .build()
        val replyIntent = Intent(context, MyReceiver::class.java)
        val replyPendingIntent = PendingIntent.getBroadcast(
            context,
            2,
            replyIntent,
            flag
        )
        val replyAction = NotificationCompat.Action.Builder(
            0,
            "Reply",
            replyPendingIntent
        ).addRemoteInput(remoteInput).build()

        val person = Person.Builder().setName("Sarvin").setIcon(IconCompat.createWithResource(context, R.drawable.baseline_person_3_24)).build()
        val notificationStyle = NotificationCompat.MessagingStyle(person)
            .addMessage("Hi there!", System.currentTimeMillis(), person)
        return NotificationCompat.Builder(context, "Third Channel ID")
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOnlyAlertOnce(true)
            //for reply action part
            .setStyle(notificationStyle)
            .addAction(replyAction)
    }


    @Singleton
    @Provides
    @SecondNotificationCompatBuilder
    fun provideSecondNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, "Second Channel ID")
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            //this line will make the notification the way that we can not close it!
            .setOngoing(true)
    }

    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "Main Channel ID",
                "Main Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val progressChannel = NotificationChannel(
                "Second Channel ID",
                "Second Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val replyChannel = NotificationChannel(
                "Third Channel ID",
                "Third Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
            notificationManager.createNotificationChannel(replyChannel)
            notificationManager.createNotificationChannel(progressChannel)
        }
        return notificationManager
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ThirdNotificationCompatBuilder


//saying to hilt that which one is which one
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainNotificationCompatBuilder

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SecondNotificationCompatBuilder