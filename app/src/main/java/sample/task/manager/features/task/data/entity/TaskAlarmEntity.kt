package sample.task.manager.features.task.data.entity

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import com.squareup.moshi.JsonClass

@Immutable
@JsonClass(generateAdapter = true)
@Entity(primaryKeys = ["taskId"])
data class TaskAlarmEntity(
    val taskId: Int = 0,
    val time: Long = 0L,
    val title: String = "",
    val description: String = ""
)
