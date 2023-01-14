package com.pluu.maxlifecycle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import com.pluu.maxlifecycle.sample.R

fun FragmentManager.showWithLifecycle(
    fragment: Fragment,
    tag: String,
    lastFragmentTag: String? = null
) {
    if (fragment.isVisible) return

    commit {
        if (tag != lastFragmentTag) {
            val lastShowFragment = findFragmentByTag(lastFragmentTag)
            if (lastShowFragment != null && lastShowFragment.isVisible) {
                hide(lastShowFragment)
                setMaxLifecycle(lastShowFragment, Lifecycle.State.STARTED)
            }
        }

        if (fragment.isAdded) {
            show(fragment)
        } else {
            add(R.id.fragmentContainer, fragment, tag)
        }
        setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
    }
}
