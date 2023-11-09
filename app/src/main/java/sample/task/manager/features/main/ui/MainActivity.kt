package sample.task.manager.features.main.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import sample.task.manager.core.theme.AppTheme
import sample.task.manager.core.util.accompanist.systemUiController.rememberSystemUiController
import sample.task.manager.core.util.snackBar.showAppSnackBar
import sample.task.manager.core.util.ui.BaseActivity
import sample.task.manager.features.main.ui.theme.ThemeType

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {

            val theme by viewModel.theme.collectAsState(initial = null)

            val isDarkTheme = when (theme) {
                ThemeType.Dark.value -> true
                ThemeType.Light.value -> false
                else -> isSystemInDarkTheme()
            }

            val coroutineScope = rememberCoroutineScope()

            val mainSnackBarHostState = remember { SnackbarHostState() }

            AppTheme(darkTheme = isDarkTheme) {

                val systemUiController = rememberSystemUiController()

                val color = MaterialTheme.colorScheme.background

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = color,
                        darkIcons = !isDarkTheme
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = color
                ) {
                    MainScreen(
                        theme = theme,
                        mainSnackBarHostState = mainSnackBarHostState,
                        showMainSnackBar = { message, type, duration ->

                            coroutineScope.launch {

                                mainSnackBarHostState.showAppSnackBar(
                                    message = message,
                                    type = type,
                                    duration = duration
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}
