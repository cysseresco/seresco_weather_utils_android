package seresco.weather.library.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import seresco.weather.library.R

class NotificationUtils(val context: Context) : Constant {
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = NOTIFICATION_CHANNEL_ID

    fun showNotification(title: String, description: String, intent: Intent) {

        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_delete)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_delete))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setStyle(Notification.BigTextStyle().bigText(description))
        } else {
            builder = Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_delete)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_delete))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setStyle(Notification.BigTextStyle().bigText(description))
        }

        notificationManager.notify(NOTIFICATION_MANAGER_ID, builder.build())

    }
}