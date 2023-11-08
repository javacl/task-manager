package sample.task.manager.features.task.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import sample.task.manager.core.db.AppDb
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TaskModule {

    @Singleton
    @Provides
    fun provideTaskService(retrofit: Retrofit): TaskService {
        return retrofit.create(TaskService::class.java)
    }

    @Singleton
    @Provides
    fun provideTaskDao(appDb: AppDb): TaskDao {
        return appDb.taskDao()
    }
}
