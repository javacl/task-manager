package sample.task.manager.core.util.alarmManager.domain

import sample.task.manager.core.util.alarmManager.AppAlarmManager
import javax.inject.Inject

class DoCancelAlarmManager @Inject constructor(
    private val appAlarmManager: AppAlarmManager
) {
    operator fun invoke(id: Int) = appAlarmManager.cancel(id)
}
