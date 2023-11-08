package sample.task.manager.features.task.data.entity

import androidx.room.Entity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(primaryKeys = ["id"])
data class TaskEntity(
    val id: Int = 0,
    val text: String = ""
)
