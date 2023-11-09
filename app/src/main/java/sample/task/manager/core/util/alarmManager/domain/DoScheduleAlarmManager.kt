package sample.task.manager.core.util.alarmManager.domain

import sample.task.manager.core.util.alarmManager.AlarmManagerItem
import sample.task.manager.core.util.alarmManager.AlarmManagerType
import sample.task.manager.core.util.alarmManager.AppAlarmManager
import javax.inject.Inject

class DoScheduleAlarmManager @Inject constructor(
    private val appAlarmManager: AppAlarmManager
) {
    operator fun invoke(
        type: AlarmManagerType,
        id: Int,
        time: Long
    ) = appAlarmManager.schedule(
        alarmManagerItem = AlarmManagerItem(
            type = type,
            id = id,
            time = time
        )
    )
}
