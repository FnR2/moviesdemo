package mobile.app.moviesdemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class DemoApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}