package com.pluu.maxlifecycle.sample

import androidx.fragment.app.Fragment

abstract class SampleFragment : Fragment() {
    abstract val key: String

    override fun toString(): String {
        return "[key=${key}]"
    }
}