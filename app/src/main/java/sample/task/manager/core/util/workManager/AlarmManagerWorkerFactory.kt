package sample.task.manager.core.util.workManager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import sample.task.manager.core.util.notification.AppNotificationManager
import sample.task.manager.features.task.domain.DoDeleteTaskAlarmLocal
import sample.task.manager.features.task.domain.GetTaskLocal
import javax.inject.Inject

class AlarmManagerWorkerFactory @Inject constructor(
    private val getTaskLocal: GetTaskLocal,
    private val appNotificationManager: AppNotificationManager,
    private val doDeleteTaskAlarmLocal: DoDeleteTaskAlarmLocal
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = AlarmManagerWorker(
        getTaskLocal = getTaskLocal,
        appNotificationManager = appNotificationManager,
        doDeleteTaskAlarmLocal = doDeleteTaskAlarmLocal,
        context = appContext,
        workerParams = workerParameters
    )
}
