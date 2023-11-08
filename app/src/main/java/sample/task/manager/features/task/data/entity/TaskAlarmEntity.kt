package sample.task.manager.features.task.data.entity

import androidx.room.Entity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(primaryKeys = ["taskId"])
data class TaskAlarmEntity(
    val taskId: Int = 0,
    val time: Long = 0L,
    val title: String = "",
    val description: String = ""
)
