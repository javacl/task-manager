package sample.task.manager.core.util.alarmManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

class AppAlarmManager(private val context: Context) {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    fun schedule(alarmManagerItem: AlarmManagerItem) {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmManagerItem.time,
            PendingIntent.getBroadcast(
                context,
                alarmManagerItem.id,
                Intent(context, AlarmManagerReceiver::class.java).apply {
                    putExtra(KEY_ALARM_MANAGER_TYPE, alarmManagerItem.type.name)
                    putExtra(KEY_ALARM_MANAGER_ID, alarmManagerItem.id)
                },
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    fun cancel(id: Int) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                id,
                Intent(context, AlarmManagerReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    companion object {

        const val KEY_ALARM_MANAGER_TYPE = "alarm_manager_type"

        const val KEY_ALARM_MANAGER_ID = "alarm_manager_id"
    }
}
