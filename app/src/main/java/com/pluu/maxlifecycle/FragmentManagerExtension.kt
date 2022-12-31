package com.pluu.maxlifecycle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import com.pluu.maxlifecycle.sample.R

fun FragmentManager.showWithLifecycle(
    fragment: Fragment,
    lastFragmentTag: String? = null
): String {
    if (fragment.isVisible) return fragment.lifecycleTag

    commit {
        val lastShowFragment = findFragmentByTag(lastFragmentTag)
        if (lastShowFragment != null && lastShowFragment.isVisible) {
            hide(lastShowFragment)
            setMaxLifecycle(lastShowFragment, Lifecycle.State.STARTED)
        }

        if (fragment.isAdded) {
            show(fragment)
        } else {
            add(R.id.fragmentContainer, fragment, fragment.lifecycleTag)
        }
        setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
    }
    return fragment.lifecycleTag
}

private val Fragment.lifecycleTag: String
    get() = "${javaClass.simpleName}-${hashCode()}"