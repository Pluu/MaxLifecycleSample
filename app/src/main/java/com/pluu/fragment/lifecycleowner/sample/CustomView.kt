package com.pluu.fragment.lifecycleowner.sample

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pluu.fragment.lifecycleowner.sample.databinding.ViewCustomBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import logcat.logcat

class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val binding: ViewCustomBinding

    init {
        binding = ViewCustomBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    private var counter = 0
    private var job: Job? = null
    private var isStart = false

    fun start(lifecycleOwner: LifecycleOwner?) {
        if (job?.isActive == true) {
            return
        }
        isStart = true

        lifecycleOwner?.lifecycleScope?.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                if (!isStart) return@repeatOnLifecycle
                isStart = true
                logcat { ">>> Counter Start" }
                job?.cancel()
                job = launch {
                    while (isActive) {
                        delay(1_000)
                        counter++
                        logcat { ">>> Counter $counter" }
                        refreshCounter()
                    }
                }
            }
        }
    }

    fun stop() {
        logcat { ">>> Counter Stop" }
        job?.cancel()
        job = null
        counter = 0
        isStart = false
        refreshCounter()
    }

    private fun refreshCounter() {
        binding.tvCounter.text = "Count $counter"
    }
}