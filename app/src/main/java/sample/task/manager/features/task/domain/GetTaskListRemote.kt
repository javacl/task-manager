package sample.task.manager.features.task.domain

import sample.task.manager.features.task.data.TaskRepository
import javax.inject.Inject

class GetTaskListRemote @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke() = taskRepository.getTaskListRemote()
}
