package sample.task.manager.features.task.data.entity

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import com.squareup.moshi.JsonClass

@Immutable
@JsonClass(generateAdapter = true)
@Entity(primaryKeys = ["id"])
data class TaskEntity(
    val id: Int = 0,
    val text: String = ""
)
