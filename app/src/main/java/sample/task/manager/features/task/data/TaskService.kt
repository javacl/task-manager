package sample.task.manager.features.task.data

import retrofit2.Response
import retrofit2.http.GET
import sample.task.manager.features.task.data.network.TaskNetwork

interface TaskService {

    @GET("ceaebb09-49c2-4b6e-a8ad-eabe9e3dee36")
    suspend fun getTaskList(): Response<List<TaskNetwork>>
}
