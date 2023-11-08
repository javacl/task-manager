package sample.task.manager.core.preferences.domain

import sample.task.manager.core.preferences.PreferencesDataStore
import javax.inject.Inject

class GetThemePrefs @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) {
    operator fun invoke() = preferencesDataStore.getTheme
}
