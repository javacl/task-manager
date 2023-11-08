package sample.task.manager.features.task.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TaskModel(
    val id: Int = 0,
    val text: String = "",
    val time: Long = 0L,
    val title: String = "",
    val description: String = ""
)
