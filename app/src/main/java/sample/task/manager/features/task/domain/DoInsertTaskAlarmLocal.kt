package sample.task.manager.features.task.domain

import sample.task.manager.core.api.ApiResult
import sample.task.manager.core.api.Exceptions
import sample.task.manager.core.util.ValidateKeys
import sample.task.manager.features.task.data.TaskRepository
import sample.task.manager.features.task.domain.validation.CreateTaskAlarmValidationError
import javax.inject.Inject

class DoInsertTaskAlarmLocal @Inject constructor(
    private val taskRepository: TaskRepository
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
            ApiResult.Success(
                taskRepository.insertTaskAlarmLocal(
                    id = id,
                    time = System.currentTimeMillis() + (mSecondsLaterFromNow * 1000L),
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

        if (secondsLaterFromNow < 1) {
            validate.secondsLaterFromNow.add(ValidateKeys.InvalidInputs)
        }

        return validate
    }
}
