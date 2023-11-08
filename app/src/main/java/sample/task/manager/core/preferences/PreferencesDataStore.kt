package sample.task.manager.core.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import sample.task.manager.BuildConfig
import sample.task.manager.features.main.ui.theme.ThemeType
import java.io.IOException

class PreferencesDataStore constructor(
    private val context: Context
) {
    private object PreferencesKeys {
        val keyTheme = intPreferencesKey("key_theme")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = BuildConfig.APPLICATION_ID + ".preferences.data.store"
    )

    val getTheme: Flow<Int> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException)
                emit(emptyPreferences())
            else throw exception
        }
        .map { preferences ->
            preferences[PreferencesKeys.keyTheme] ?: ThemeType.SystemDefault.value
        }

    suspend fun updateTheme(value: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.keyTheme] = value
        }
    }
}
