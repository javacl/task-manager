package sample.task.manager.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import sample.task.manager.features.task.data.TaskDao
import sample.task.manager.features.task.data.entity.TaskAlarmEntity
import sample.task.manager.features.task.data.entity.TaskEntity

@Database(
    entities = [
        TaskEntity::class,
        TaskAlarmEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {

    abstract fun taskDao(): TaskDao
}
