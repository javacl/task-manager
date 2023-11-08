package sample.task.manager.features.task.data.dto

import androidx.room.Embedded
import androidx.room.Relation
import com.squareup.moshi.JsonClass
import sample.task.manager.features.task.data.entity.TaskAlarmEntity
import sample.task.manager.features.task.data.entity.TaskEntity
import sample.task.manager.features.task.data.model.TaskModel

@JsonClass(generateAdapter = true)
data class TaskDto(
    @Embedded
    val task: TaskEntity = TaskEntity(),
    @Relation(
        parentColumn = "id",
        entityColumn = "taskId",
        entity = TaskAlarmEntity::class
    )
    val taskAlarm: TaskAlarmEntity = TaskAlarmEntity()
) {
    fun toTaskModel() = TaskModel(
        id = task.id,
        text = task.text,
        time = taskAlarm.time,
        title = taskAlarm.title,
        description = taskAlarm.description
    )
}
