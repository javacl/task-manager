package sample.task.manager.features.task.domain

import sample.task.manager.core.api.ApiResult
import sample.task.manager.core.util.alarmManager.domain.DoCancelAlarmManager
import sample.task.manager.features.task.data.TaskRepository
import javax.inject.Inject

class DoDeleteTaskAlarmLocal @Inject constructor(
    private val taskRepository: TaskRepository,
    private val doCancelAlarmManager: DoCancelAlarmManager
) {
    suspend operator fun invoke(id: Int): ApiResult<Unit> {

        doCancelAlarmManager(id)

        return ApiResult.Success(
            taskRepository.deleteTaskAlarmLocal(id)
        )
    }
}
