package sample.task.manager.features.task.data

import androidx.room.withTransaction
import kotlinx.coroutines.flow.map
import sample.task.manager.core.db.AppDb
import sample.task.manager.features.task.data.entity.TaskAlarmEntity
import sample.task.manager.features.task.data.entity.TaskEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskLocalDataSource @Inject constructor(
    private val appDb: AppDb,
    private val taskDao: TaskDao
) {
    fun getTaskList() = taskDao.getTaskList().map {
        it?.map { item -> item.toTaskModel() }
    }

    fun getTask(id: Int) = taskDao.getTask(id).map {
        it?.toTaskModel()
    }

    suspend fun insertTaskList(value: List<TaskEntity>) {
        appDb.withTransaction {
            taskDao.deleteTaskList()
            taskDao.insertTaskList(value)
        }
    }


    fun getTaskAlarmList() = taskDao.getTaskAlarmList()

    suspend fun insertTaskAlarm(
        id: Int,
        time: Long,
        title: String,
        description: String
    ) {
        appDb.withTransaction {
            taskDao.insertTaskAlarm(
                TaskAlarmEntity(
                    taskId = id,
                    time = time,
                    title = title,
                    description = description
                )
            )
        }
    }

    suspend fun deleteTaskAlarm(id: Int) {
        appDb.withTransaction {
            taskDao.deleteTaskAlarm(id)
        }
    }
}
