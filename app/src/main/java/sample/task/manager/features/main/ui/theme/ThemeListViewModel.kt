package sample.task.manager.features.main.ui.theme

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import sample.task.manager.core.preferences.domain.DoUpdateThemePrefs
import sample.task.manager.core.preferences.domain.GetThemePrefs
import sample.task.manager.core.util.viewModel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeListViewModel @Inject constructor(
    getThemePrefs: GetThemePrefs,
    private val updateThemePrefs: DoUpdateThemePrefs
) : BaseViewModel() {

    val theme = getThemePrefs()
        .flowOn(Dispatchers.IO)

    fun updateTheme(value: Int) {
        runBlocking { updateThemePrefs(value) }
    }
}
