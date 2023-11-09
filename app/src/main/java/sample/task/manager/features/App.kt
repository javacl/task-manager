package sample.task.manager.features

import android.util.Log
import androidx.multidex.MultiDexApplication
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import sample.task.manager.core.util.workManager.AlarmManagerWorkerFactory
import javax.inject.Inject

@HiltAndroidApp
class App : MultiDexApplication(), Configuration.Provider {

    @Inject
    lateinit var alarmManagerWorkerFactory: AlarmManagerWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(alarmManagerWorkerFactory)
            .build()
    }
}
