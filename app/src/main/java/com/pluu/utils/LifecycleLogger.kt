package com.pluu.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import logcat.logcat

private val Any.lifecycleSimpleName: String
    get() = javaClass.simpleName

private fun Activity.printInfo(message: String) {
    logcat {
        "$lifecycleSimpleName $message"
    }
}

private fun Fragment.printInfo(message: String) {
    logcat {
        "$lifecycleSimpleName(${hashCode()}) ${toString()} $message"
    }
}

fun registerLifecycleCallbacks(application: Application) {
    application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activity.printInfo("Created")
            registerLifecycleCallbacks(activity)
        }

        override fun onActivityStarted(activity: Activity) {
            activity.printInfo("Started")
        }

        override fun onActivityResumed(activity: Activity) {
            activity.printInfo("Resumed")
        }

        override fun onActivityPaused(activity: Activity) {
            activity.printInfo("Paused")
        }

        override fun onActivityStopped(activity: Activity) {
            activity.printInfo("Stopped")
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            activity.printInfo("SaveInstanceState")
        }

        override fun onActivityDestroyed(activity: Activity) {
            activity.printInfo("Destroyed")
        }
    })
}

fun registerLifecycleCallbacks(activity: Activity) {
    if (activity is FragmentActivity) {
        activity.supportFragmentManager.autoBindLifecycle()
        activity.supportFragmentManager.autoBackStackTracking()
    }
}

private fun FragmentManager.autoBindLifecycle() {
    registerFragmentLifecycleCallbacks(
        object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentAttached(
                fm: FragmentManager,
                f: Fragment,
                context: Context
            ) {
                super.onFragmentAttached(fm, f, context)
                f.printInfo("Attached")
            }

            override fun onFragmentCreated(
                fm: FragmentManager,
                f: Fragment,
                savedInstanceState: Bundle?
            ) {
                super.onFragmentCreated(fm, f, savedInstanceState)
                fm.autoBackStackTracking()
                f.printInfo("Created")
            }

            override fun onFragmentViewCreated(
                fm: FragmentManager,
                f: Fragment,
                v: View,
                savedInstanceState: Bundle?
            ) {
                super.onFragmentViewCreated(fm, f, v, savedInstanceState)
                f.printInfo("ViewCreated")
            }

            override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
                super.onFragmentStarted(fm, f)
                f.printInfo("Started")
            }

            override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                super.onFragmentResumed(fm, f)
                f.printInfo("Resumed")
            }

            override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
                super.onFragmentPaused(fm, f)
                f.printInfo("Paused")
            }

            override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
                super.onFragmentStopped(fm, f)
                f.printInfo("Stopped")
            }

            override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                super.onFragmentViewDestroyed(fm, f)
                f.printInfo("ViewDestroyed")
            }

            override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
                super.onFragmentDestroyed(fm, f)
                f.printInfo("Destroyed")
            }

            override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
                super.onFragmentDetached(fm, f)
                f.printInfo("Detached")
            }

            override fun onFragmentSaveInstanceState(
                fm: FragmentManager,
                f: Fragment,
                outState: Bundle
            ) {
                super.onFragmentSaveInstanceState(fm, f, outState)
                f.printInfo("SaveInstanceState")
            }
        }, true
    )
}

private fun FragmentManager.autoBackStackTracking() {
//    addOnBackStackChangedListener {
//        for (entry in 0 until backStackEntryCount) {
//            logcat {
//                "Found fragment: %s".format(getBackStackEntryAt(entry).id)
//            }
//        }
//    }
}