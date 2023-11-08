package sample.task.manager.features.task.domain

import sample.task.manager.features.task.data.TaskRepository
import javax.inject.Inject

class GetTaskListLocal @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke() = taskRepository.getTaskListLocal()
}
