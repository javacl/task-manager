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
        time: Long,
        title: String,
        description: String
    ): ApiResult<Unit> {

        val validate = validateCreateTaskAlarm(time = time)

        return if (validate.isValid()) {
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

    private fun validateCreateTaskAlarm(time: Long): CreateTaskAlarmValidationError {

        val validate = CreateTaskAlarmValidationError()

        if (time < System.currentTimeMillis() + 5000) {
            validate.time.add(ValidateKeys.InvalidInputs)
        }

        return validate
    }
}
