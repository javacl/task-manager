package sample.task.manager.features.task.domain

import sample.task.manager.core.api.ApiResult
import sample.task.manager.core.api.Exceptions
import sample.task.manager.core.util.ValidateKeys
import sample.task.manager.core.util.alarmManager.AlarmManagerType
import sample.task.manager.core.util.alarmManager.domain.DoScheduleAlarmManager
import sample.task.manager.features.task.data.TaskRepository
import sample.task.manager.features.task.domain.validation.CreateTaskAlarmValidationError
import javax.inject.Inject

class DoInsertTaskAlarmLocal @Inject constructor(
    private val taskRepository: TaskRepository,
    private val doScheduleAlarmManager: DoScheduleAlarmManager
) {
    suspend operator fun invoke(
        id: Int,
        secondsLaterFromNow: String,
        title: String,
        description: String
    ): ApiResult<Unit> {

        val mSecondsLaterFromNow = secondsLaterFromNow.toLongOrNull() ?: 0L

        val validate = validateCreateTaskAlarm(
            secondsLaterFromNow = mSecondsLaterFromNow
        )

        return if (validate.isValid()) {

            val time = System.currentTimeMillis() + (mSecondsLaterFromNow * 1000L)

            doScheduleAlarmManager(
                type = AlarmManagerType.Task,
                id = id,
                time = time
            )

            ApiResult.Success(
                taskRepository.insertTaskAlarmLocal(
                    id = id,
                    time = time,
                    title = title,
                    description = description
                )
            )
        } else ApiResult.Error(Exceptions.ValidationException(validate))
    }

    private fun validateCreateTaskAlarm(
        secondsLaterFromNow: Long
    ): CreateTaskAlarmValidationError {

        val validate = CreateTaskAlarmValidationError()

        if (secondsLaterFromNow < 5) {
            validate.secondsLaterFromNow.add(ValidateKeys.MinSeconds)
        } else if (secondsLaterFromNow > 3600) {
            validate.secondsLaterFromNow.add(ValidateKeys.MaxSeconds)
        }

        return validate
    }
}
