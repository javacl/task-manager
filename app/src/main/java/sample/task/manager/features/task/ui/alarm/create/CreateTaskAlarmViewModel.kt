package sample.task.manager.features.task.ui.alarm.create

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import sample.task.manager.core.util.navigation.NavigationKey
import sample.task.manager.core.util.viewModel.BaseViewModel
import sample.task.manager.features.task.domain.DoInsertTaskAlarmLocal
import sample.task.manager.features.task.domain.GetTaskLocal
import javax.inject.Inject

@HiltViewModel
class CreateTaskAlarmViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTaskLocal: GetTaskLocal,
    private val doInsertTaskAlarmLocal: DoInsertTaskAlarmLocal
) : BaseViewModel() {

    private val id by lazy {
        savedStateHandle.get<String>(NavigationKey.KEY_ID)?.toInt() ?: 0
    }

    val task = getTaskLocal(id)

    private val _secondsLaterFromNow = MutableStateFlow("")
    val secondsLaterFromNow = _secondsLaterFromNow.asStateFlow()

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _notValidSecondsFromNow = MutableStateFlow(false)
    val notValidSecondsFromNow = _notValidSecondsFromNow.asStateFlow()

    init {

        task
            .onEach {

                if (it?.time != null) {

                    val seconds = (it.time - System.currentTimeMillis()) / 1000L

                    if (seconds > 0) {
                        setSecondsLaterFromNow(seconds.toString())
                    }
                }

                setTitle(it?.title ?: "")
                setDescription(it?.description ?: "")
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun setSecondsLaterFromNow(value: String) {
        if (value.length <= 6) {
            _secondsLaterFromNow.value = value.filter { it.isDigit() }
            setNotValidSecondsLaterFromNow(false)
        }
    }

    fun setTitle(value: String) {
        if (value.length < 25) {
            _title.value = value
        }
    }

    fun setDescription(value: String) {
        if (value.length < 100) {
            _description.value = value
        }
    }

    fun setNotValidSecondsLaterFromNow(value: Boolean) {
        _notValidSecondsFromNow.value = value
    }

    fun insertTaskAlarm() {
        viewModelScope.launch(Dispatchers.IO) {
            networkLoading()
            observeNetworkState(
                doInsertTaskAlarmLocal(
                    id = id,
                    secondsLaterFromNow = _secondsLaterFromNow.value,
                    title = _title.value,
                    description = _description.value
                )
            )
        }
    }
}
