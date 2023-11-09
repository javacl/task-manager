package sample.task.manager.core.util.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import sample.task.manager.core.util.notification.AppNotificationManager
import sample.task.manager.features.task.domain.DoDeleteTaskAlarmLocal
import sample.task.manager.features.task.domain.GetTaskLocal
import javax.inject.Inject

@AndroidEntryPoint
class AlarmManagerReceiver : BroadcastReceiver() {

    @Inject
    lateinit var getTaskLocal: GetTaskLocal

    @Inject
    lateinit var appNotificationManager: AppNotificationManager

    @Inject
    lateinit var doDeleteTaskAlarmLocal: DoDeleteTaskAlarmLocal

    override fun onReceive(context: Context?, intent: Intent?) {

        when (intent?.getStringExtra(AppAlarmManager.KEY_ALARM_MANAGER_TYPE)) {

            AlarmManagerType.Task.name -> {

                val id = intent.getIntExtra(AppAlarmManager.KEY_ALARM_MANAGER_ID, 0)

                val task = runBlocking { getTaskLocal(id).first() }

                task?.run {
                    appNotificationManager.notify(
                        id = id,
                        title = text,
                        content = title ?: description ?: ""
                    )
                }

                runBlocking {
                    doDeleteTaskAlarmLocal(id)
                }
            }
        }

        Log.d("kir", "kira")
    }
}
