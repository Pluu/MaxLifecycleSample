package com.pluu.fragment.lifecycleowner.sample

import android.app.Application
import com.pluu.sample.myapplication.util.registerLifecycleCallbacks
import logcat.AndroidLogcatLogger
import logcat.LogPriority

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidLogcatLogger.installOnDebuggableApp(
            this,
            minPriority = LogPriority.VERBOSE
        )
        registerLifecycleCallbacks(this)
    }
}