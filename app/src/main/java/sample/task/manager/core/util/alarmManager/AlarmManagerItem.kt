package sample.task.manager.core.util.alarmManager

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlarmManagerItem(
    val type: AlarmManagerType,
    val id: Int,
    val time: Long
)
