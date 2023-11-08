package sample.task.manager.features.task.domain

import sample.task.manager.features.task.data.TaskRepository
import javax.inject.Inject

class GetTaskAlarmListLocal @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke() = taskRepository.getTaskAlarmListLocal()
}
