package sample.task.manager.features.task.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TaskModel(
    val id: Int = 0,
    val text: String = "",
    val time: Long? = null,
    val title: String? = null,
    val description: String? = null
)
