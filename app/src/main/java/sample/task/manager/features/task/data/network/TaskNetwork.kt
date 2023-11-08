package sample.task.manager.features.task.data.network

import androidx.compose.runtime.Immutable
import com.squareup.moshi.JsonClass
import sample.task.manager.features.task.data.entity.TaskEntity

@Immutable
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
