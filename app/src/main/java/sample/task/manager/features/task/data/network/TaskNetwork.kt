package sample.task.manager.features.task.data.network

import com.squareup.moshi.JsonClass
import sample.task.manager.features.task.data.entity.TaskEntity

@JsonClass(generateAdapter = true)
data class TaskNetwork(
    val id: Int = 0,
    val text: String = ""
) {
    fun toTaskEntity() = TaskEntity(
        id = id,
        text = text
    )
}
