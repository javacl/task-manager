package sample.task.manager.core.preferences.domain

import sample.task.manager.core.preferences.PreferencesDataStore
import javax.inject.Inject

class DoUpdateThemePrefs @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) {
    suspend operator fun invoke(value: Int) = preferencesDataStore.updateTheme(value)
}
