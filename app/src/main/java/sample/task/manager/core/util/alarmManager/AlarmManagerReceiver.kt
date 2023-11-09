package sample.task.manager.core.util.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import sample.task.manager.core.util.workManager.AlarmManagerWorker
import javax.inject.Inject

@AndroidEntryPoint
class AlarmManagerReceiver : BroadcastReceiver() {

    @Inject
    lateinit var workManager: WorkManager

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent != null) {

            val type = intent.getStringExtra(AppAlarmManager.KEY_ALARM_MANAGER_TYPE)

            val id = intent.getIntExtra(AppAlarmManager.KEY_ALARM_MANAGER_ID, 0)

            workManager.enqueue(
                OneTimeWorkRequestBuilder<AlarmManagerWorker>()
                    .setInputData(
                        Data.Builder()
                            .putString(AppAlarmManager.KEY_ALARM_MANAGER_TYPE, type)
                            .putInt(AppAlarmManager.KEY_ALARM_MANAGER_ID, id)
                            .build()
                    ).build()
            )
        }
    }
}
