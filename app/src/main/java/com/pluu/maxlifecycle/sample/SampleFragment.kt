package com.pluu.maxlifecycle.sample

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.pluu.utils.SimpleLifecycleOwner

abstract class SampleFragment : Fragment() {
    abstract val key: String

    protected val visibleLifecycleOwner: SimpleLifecycleOwner by lazy {
        SimpleLifecycleOwner()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fragment show/hidden 지원하는 Lifecycle Owner 대응
        viewLifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_RESUME,
                    Lifecycle.Event.ON_PAUSE -> {
                        if (isHidden) {
                            visibleLifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
                        } else {
                            visibleLifecycleOwner.handleLifecycleEvent(event)
                        }
                    }
                    else -> {
                        visibleLifecycleOwner.handleLifecycleEvent(event)
                    }
                }
            }
        })
    }

    override fun toString(): String {
        return "[key=${key}]"
    }
}