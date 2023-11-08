package sample.task.manager.features.task.data

import sample.task.manager.core.api.ApiResult
import sample.task.manager.core.api.Exceptions
import sample.task.manager.core.util.NetworkHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskLocalDataSource: TaskLocalDataSource,
    private val taskRemoteDataSource: TaskRemoteDataSource,
    private val networkHandler: NetworkHandler
) {
    fun getTaskListLocal() = taskLocalDataSource.getTaskList()

    fun getTaskLocal(id: Int) = taskLocalDataSource.getTask(id)

    suspend fun getTaskListRemote(): ApiResult<Unit> {
        return if (networkHandler.hasNetworkConnection()) {
            when (val result = taskRemoteDataSource.getTaskList()) {
                is ApiResult.Success -> ApiResult.Success(
                    taskLocalDataSource.insertTaskList(
                        result.data.map { it.toTaskEntity() }
                    )
                )

                is ApiResult.Error -> ApiResult.Error(result.exception)
            }
        } else ApiResult.Error(Exceptions.NetworkConnectionException())
    }


    fun getTaskAlarmListLocal() = taskLocalDataSource.getTaskAlarmList()

    suspend fun insertTaskAlarmLocal(
        id: Int,
        time: Long,
        title: String,
        description: String
    ) = taskLocalDataSource.insertTaskAlarm(
        id = id,
        time = time,
        title = title,
        description = description
    )

    suspend fun deleteTaskAlarmLocal(id: Int) = taskLocalDataSource.deleteTaskAlarm(id)
}
