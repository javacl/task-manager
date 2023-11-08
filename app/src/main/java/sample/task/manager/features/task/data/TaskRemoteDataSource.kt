package sample.task.manager.features.task.data

import sample.task.manager.core.api.BaseRemoteDataSource
import sample.task.manager.core.api.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRemoteDataSource @Inject constructor(
    private val taskService: TaskService
) : BaseRemoteDataSource() {

    suspend fun getTaskList() = safeApiCall(
        call = { requestGetTaskList() },
        errorMessage = "Error get task list"
    )

    private suspend fun requestGetTaskList() = checkApiResult(
        taskService.getTaskList()
    )
}
