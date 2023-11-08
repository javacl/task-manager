package sample.task.manager.features.task.ui.alarm.delete

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sample.task.manager.core.util.navigation.NavigationKey
import sample.task.manager.core.util.viewModel.BaseViewModel
import sample.task.manager.features.task.domain.DoDeleteTaskAlarmLocal
import sample.task.manager.features.task.domain.GetTaskLocal
import javax.inject.Inject

@HiltViewModel
class DeleteTaskAlarmViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTaskLocal: GetTaskLocal,
    private val doDeleteTaskAlarmLocal: DoDeleteTaskAlarmLocal
) : BaseViewModel() {

    private val id by lazy {
        savedStateHandle.get<String>(NavigationKey.KEY_ID)?.toInt() ?: 0
    }

    val task = getTaskLocal(id)

    fun deleteTaskAlarm() {
        viewModelScope.launch(Dispatchers.IO) {
            networkLoading()
            observeNetworkState(
                doDeleteTaskAlarmLocal(id)
            )
        }
    }
}
