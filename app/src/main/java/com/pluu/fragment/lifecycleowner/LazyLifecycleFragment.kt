package com.pluu.fragment.lifecycleowner

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

abstract class LazyLifecycleFragment : Fragment() {

    abstract val lazyKey: String

    private val lazyFragmentViewLifecycleOwner: LazyFragmentViewLifecycleOwner by lazy {
        LazyFragmentViewLifecycleOwner(this, lazyKey)
    }

    override fun getViewLifecycleOwner(): LifecycleOwner {
        return lazyFragmentViewLifecycleOwner
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            lazyFragmentViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        } else {
            lazyFragmentViewLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }
    }
}