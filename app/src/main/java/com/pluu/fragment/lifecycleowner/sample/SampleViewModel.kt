package com.pluu.fragment.lifecycleowner.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import logcat.logcat
import kotlin.random.Random

class SampleViewModel : ViewModel() {
    private val _test = MutableLiveData<String>()
    val test: LiveData<String> get() = _test

    fun load() {
        logcat { "load start" }
        viewModelScope.launch {
            delay(3_000)
            val value = Random.nextInt(5_000_000).toString()
            logcat { "Data Send ==> $value" }
            _test.value = value
        }
    }
}