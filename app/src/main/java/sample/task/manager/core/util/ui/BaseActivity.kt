package sample.task.manager.core.util.ui

import android.content.Context
import androidx.activity.ComponentActivity
import sample.task.manager.core.util.localizedContext

abstract class BaseActivity : ComponentActivity() {

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(localizedContext(context))
    }

    override fun onStart() {
        super.onStart()
        localizedContext(this)
    }
}
