package sample.task.manager.features.main.ui.theme

import sample.task.manager.R

object ThemeUtil {

    fun getThemeName(value: Int?): Int {
        return when (value) {
            ThemeType.Dark.value -> R.string.label_dark
            ThemeType.Light.value -> R.string.label_light
            else -> R.string.label_system_default
        }
    }
}
