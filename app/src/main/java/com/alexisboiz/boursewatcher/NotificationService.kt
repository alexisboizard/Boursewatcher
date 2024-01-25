package com.alexisboiz.boursewatcher

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.alexisboiz.boursewatcher.views.friends_fragment.FriendRequestActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class NotificationService : FirebaseMessagingService()  {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body

        val myIntent = Intent(this.baseContext, FriendRequestActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this.baseContext,
            0,
            myIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val CHANNEL_ID = "HEADS_UP_NOTIFICATION"

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Heads Up Notification",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "This is the channel for heads up notifications"
        }

        val notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setChannelId(CHANNEL_ID)
            .setFullScreenIntent(pendingIntent, true)
            .setWhen(System.currentTimeMillis())
            .setShowWhen(true)
            .build()

        NotificationManagerCompat.from(this).apply {
            createNotificationChannel(channel)
            if (ActivityCompat.checkSelfPermission(
                    baseContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, notification)
        }

    }
    // [END receive_message]

    private fun needsToBeScheduled() = true
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }
    // [END on_new_token]

    private fun scheduleJob() {

    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }

    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

    companion object {
        private const val TAG = "NotificationService"
    }

}