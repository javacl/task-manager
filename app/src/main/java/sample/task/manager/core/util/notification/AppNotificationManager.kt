package sample.task.manager.core.util.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import sample.task.manager.R
import sample.task.manager.core.theme.md_theme_light_primary

class AppNotificationManager(private val context: Context) {

    fun notify(
        id: Int = 0,
        title: String,
        content: String,
        priority: Int = NotificationCompat.PRIORITY_HIGH,
        color: Int = md_theme_light_primary.toArgb()
    ) {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(content)
            .setColor(color)
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(priority)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle())
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    0,
                    context.packageManager.getLaunchIntentForPackage(context.packageName),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )

        val notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "unknown",
                NotificationManager.IMPORTANCE_HIGH,
            ).apply {
                lightColor = color
            }

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id, notification.build())
    }

    companion object {

        const val NOTIFICATION_CHANNEL_ID = "notification"
    }
}
