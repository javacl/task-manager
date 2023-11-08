package sample.task.manager.features.task.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import sample.task.manager.features.task.data.dto.TaskDto
import sample.task.manager.features.task.data.entity.TaskAlarmEntity
import sample.task.manager.features.task.data.entity.TaskEntity

@Dao
interface TaskDao {

    @Transaction
    @Query("SELECT * FROM TaskEntity")
    fun getTaskList(): Flow<List<TaskDto>?>

    @Transaction
    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    fun getTask(id: Int): Flow<TaskDto?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskList(value: List<TaskEntity>?)

    @Query("DELETE FROM TaskEntity")
    suspend fun deleteTaskList()


    @Transaction
    @Query("SELECT * FROM TaskAlarmEntity")
    fun getTaskAlarmList(): Flow<List<TaskAlarmEntity>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskAlarm(value: TaskAlarmEntity?)

    @Query("DELETE FROM TaskAlarmEntity WHERE taskId = :id")
    suspend fun deleteTaskAlarm(id: Int)
}
