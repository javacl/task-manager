package sample.task.manager.features.task.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sample.task.manager.core.util.viewModel.BaseViewModel
import sample.task.manager.features.task.domain.GetTaskListLocal
import sample.task.manager.features.task.domain.GetTaskListRemote
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    getTaskListLocal: GetTaskListLocal,
    private val getTaskListRemote: GetTaskListRemote
) : BaseViewModel() {

    val taskList = getTaskListLocal()

    init {
        getData()
    }

    private fun getData() {

        viewModelScope.launch(Dispatchers.IO) {

            networkLoading(TaskRequestTag.GetTaskList.name)

            observeNetworkState(
                getTaskListRemote(),
                TaskRequestTag.GetTaskList.name
            )
        }
    }

    fun refresh() {
        getData()
    }
}
