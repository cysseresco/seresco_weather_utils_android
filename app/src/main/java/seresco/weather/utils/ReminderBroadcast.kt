package seresco.weather.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import seresco.weather.library.utils.Constant
import seresco.weather.library.utils.NotificationPreferences

class ReminderBroadcast: BroadcastReceiver(), Constant {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val context = p0 ?: return
        val notificationPreferences = NotificationPreferences(context)
        val skyStatusContent = notificationPreferences.getSkyStatus(NOTIFICATION_PREFERENCES_SKY_STATUS)
        Log.e("hey! nanniiiii", skyStatusContent.toString())
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(skyStatusContent?.title)
            .setContentText(skyStatusContent?.body)
            .setPriority(NotificationCompat.PRIORITY_MAX)

        val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManager.notify(NOTIFICATION_ID, builder.build())
        notificationPreferences.clearPreferences()
    }
}