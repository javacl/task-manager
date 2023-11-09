package sample.task.manager.core.util.workManager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import sample.task.manager.core.util.alarmManager.AlarmManagerType
import sample.task.manager.core.util.alarmManager.AppAlarmManager
import sample.task.manager.core.util.notification.AppNotificationManager
import sample.task.manager.features.task.domain.DoDeleteTaskAlarmLocal
import sample.task.manager.features.task.domain.GetTaskLocal

@HiltWorker
class AlarmManagerWorker @AssistedInject constructor(
    @Assisted private val getTaskLocal: GetTaskLocal,
    @Assisted private val appNotificationManager: AppNotificationManager,
    @Assisted private val doDeleteTaskAlarmLocal: DoDeleteTaskAlarmLocal,
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        when (inputData.getString(AppAlarmManager.KEY_ALARM_MANAGER_TYPE)) {

            AlarmManagerType.Task.name -> {

                val id = inputData.getInt(
                    AppAlarmManager.KEY_ALARM_MANAGER_ID,
                    0
                )

                getTaskLocal(id).first()?.run {
                    appNotificationManager.notify(
                        id = id,
                        title = text,
                        content = description ?: title ?: ""
                    )
                }

                doDeleteTaskAlarmLocal(id)
            }
        }

        return Result.success()
    }
}
