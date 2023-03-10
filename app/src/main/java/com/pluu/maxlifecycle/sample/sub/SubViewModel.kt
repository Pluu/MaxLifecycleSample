package com.pluu.maxlifecycle.sample.sub

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import logcat.logcat
import kotlin.random.Random

class SubViewModel : ViewModel() {
    private val _test = MutableLiveData<String>()
    val test: LiveData<String> get() = _test

    fun load() {
        logcat { "load start" }
        viewModelScope.launch {
            val totalCount = 3
            repeat(totalCount) {
                logcat { "Count down ${totalCount - it}" }
                delay(1_000)
            }
            val value = Random.nextInt(5_000_000).toString()
            logcat { "Data Send ==> $value" }
            _test.value = value
        }
    }
}