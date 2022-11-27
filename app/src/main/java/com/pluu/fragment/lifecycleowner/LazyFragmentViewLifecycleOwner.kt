package com.pluu.fragment.lifecycleowner

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import logcat.logcat

class LazyFragmentViewLifecycleOwner(
    private val fragment: Fragment,
    private val key: String
) : LifecycleOwner {
    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    init {
        lifecycleRegistry.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                super.onCreate(owner)
                logcat { ">> $key onCreate" }
            }

            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                logcat { ">> $key onStart" }
            }

            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                logcat { ">> $key onResume" }
            }

            override fun onPause(owner: LifecycleOwner) {
                super.onPause(owner)
                logcat { ">> $key onPause" }
            }

            override fun onStop(owner: LifecycleOwner) {
                super.onStop(owner)
                logcat { ">> $key onStop" }
            }

            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                logcat { ">> $key onDestroy" }
            }
        })

        fragment.viewLifecycleOwnerLiveData.observeForever { lifecycleOwner ->
            // Update, original viewLifecycleOwner
            if (lifecycleOwner == null) {
                // destroy FragmentView
                return@observeForever
            }

            lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onCreate(owner: LifecycleOwner) {
                    super.onCreate(owner)
                    handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
                }

                override fun onStart(owner: LifecycleOwner) {
                    super.onStart(owner)
                    if (fragment.isHidden) {
                        handleLifecycleEvent(Lifecycle.Event.ON_STOP)
                    } else {
                        handleLifecycleEvent(Lifecycle.Event.ON_START)
                    }
                }

                override fun onResume(owner: LifecycleOwner) {
                    super.onResume(owner)
                    if (fragment.isHidden) {
                        handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
                    } else {
                        handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
                    }
                }

                override fun onPause(owner: LifecycleOwner) {
                    super.onPause(owner)
                    handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
                }

                override fun onStop(owner: LifecycleOwner) {
                    super.onStop(owner)
                    handleLifecycleEvent(Lifecycle.Event.ON_STOP)
                }

                override fun onDestroy(owner: LifecycleOwner) {
                    super.onDestroy(owner)
                    handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                }
            })
        }
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

    fun setCurrentState(state: Lifecycle.State) {
        lifecycleRegistry.currentState = state
    }

    fun handleLifecycleEvent(event: Lifecycle.Event) {
        lifecycleRegistry.handleLifecycleEvent(event)
    }
}