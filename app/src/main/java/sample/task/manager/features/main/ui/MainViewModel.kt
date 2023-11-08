package sample.task.manager.features.main.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import sample.task.manager.core.preferences.domain.GetThemePrefs
import sample.task.manager.core.util.viewModel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getThemePrefs: GetThemePrefs
) : BaseViewModel() {

    val theme = getThemePrefs()
        .flowOn(Dispatchers.IO)
}
