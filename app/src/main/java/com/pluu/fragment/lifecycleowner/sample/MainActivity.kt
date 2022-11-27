package com.pluu.fragment.lifecycleowner.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.pluu.fragment.lifecycleowner.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val screen1 by lazy {
        SampleFragment.newInstance("Sample1", "FFFF0000".toLong(16).toInt())
    }
    private val screen2 by lazy {
        SampleFragment.newInstance("Sample2", "FF1565C0".toLong(16).toInt())
    }
    private val screen3 by lazy {
        SampleFragment.newInstance("Sample3", "FFFF6F00".toLong(16).toInt())
    }
    private val screen4 by lazy {
        SampleFragment.newInstance("Sample4", "FF85BB5C".toLong(16).toInt())
    }

    private var lastFragmentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fun replace(fragment: Fragment, tag: String) {
            if (fragment.isVisible) return
            supportFragmentManager.commit {
                val lastShowFragment = supportFragmentManager.findFragmentByTag(lastFragmentTag)
                if (lastShowFragment != null && lastShowFragment.isVisible) {
                    hide(lastShowFragment)
                }
                lastFragmentTag = tag
                if (fragment.isAdded) {
                    show(fragment)
                } else {
                    add(R.id.fragmentContainer, fragment, tag)
                }
            }
        }

        replace(screen1, "screen1")

        binding.btn1.setOnClickListener {
            replace(screen1, "screen1")
        }
        binding.btn2.setOnClickListener {
            replace(screen2, "screen2")
        }
        binding.btn3.setOnClickListener {
            replace(screen3, "screen3")
        }
        binding.btn4.setOnClickListener {
            replace(screen4, "screen4")
        }
    }
}