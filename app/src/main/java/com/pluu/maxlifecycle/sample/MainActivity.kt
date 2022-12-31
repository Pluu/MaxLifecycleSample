package com.pluu.maxlifecycle.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.pluu.maxlifecycle.sample.databinding.ActivityMainBinding
import com.pluu.maxlifecycle.sample.menu.MenuFragment
import com.pluu.maxlifecycle.sample.menu.MenuWithContainerFragment
import com.pluu.maxlifecycle.showWithLifecycle

class MainActivity : AppCompatActivity() {

    private val screen1 by lazy {
        MenuWithContainerFragment.newInstance("Menu1")
    }
    private val screen2 by lazy {
        MenuFragment.newInstance("Menu2")
    }
    private val screen3 by lazy {
        MenuFragment.newInstance("Menu3")
    }
    private val screen4 by lazy {
        MenuFragment.newInstance("Menu4")
    }

    private var lastFragmentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replace(screen1)

        binding.btn1.setOnClickListener {
            replace(screen1)
        }
        binding.btn2.setOnClickListener {
            replace(screen2)
        }
        binding.btn3.setOnClickListener {
            replace(screen3)
        }
        binding.btn4.setOnClickListener {
            replace(screen4)
        }
    }

    private fun replace(fragment: Fragment) {
        lastFragmentTag = supportFragmentManager.showWithLifecycle(fragment, lastFragmentTag)
    }
}