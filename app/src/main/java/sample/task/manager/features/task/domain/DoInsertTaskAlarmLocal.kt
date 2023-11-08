package sample.task.manager.features.task.domain

import sample.task.manager.core.api.ApiResult
import sample.task.manager.features.task.data.TaskRepository
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
        return ApiResult.Success(
            taskRepository.insertTaskAlarmLocal(
                id = id,
                time = time,
                title = title,
                description = description
            )
        )
    }
}
