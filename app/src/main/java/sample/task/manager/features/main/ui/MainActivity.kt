package sample.task.manager.features.main.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import sample.task.manager.core.theme.AppTheme
import sample.task.manager.core.util.snackBar.showAppSnackBar
import sample.task.manager.core.util.ui.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {

            val coroutineScope = rememberCoroutineScope()

            val mainSnackBarHostState = remember { SnackbarHostState() }

            AppTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
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

        /*val alarmManager = getSystemService(AlarmManager::class.java)

        val intent = Intent(this, AlarmReceiver::class.java)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 1000,
            PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 10000,
            PendingIntent.getBroadcast(
                this,
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )*/
    }
}
