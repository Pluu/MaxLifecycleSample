package com.pluu.maxlifecycle.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.pluu.maxlifecycle.sample.databinding.ActivityMainBinding
import com.pluu.maxlifecycle.sample.menu.MenuFragment
import com.pluu.maxlifecycle.sample.menu.MenuWithContainerFragment
import com.pluu.maxlifecycle.showWithLifecycle

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    enum class MainMenuType {
        MENU1, MENU2, MENU3, MENU4;

        val tag: String
            get() = name.uppercase()
    }

    private var lastFragmentTag: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lastFragmentTag = savedInstanceState?.getString(LAST_FRAGMENT_TAG)
        val mainMenuType = findMenuByTag(lastFragmentTag)
        bindMenu(mainMenuType)

        setUpViews()
    }

    private fun setUpViews() {
        binding.btn1.setOnClickListener {
            bindMenu(MainMenuType.MENU1)
        }
        binding.btn2.setOnClickListener {
            bindMenu(MainMenuType.MENU2)
        }
        binding.btn3.setOnClickListener {
            bindMenu(MainMenuType.MENU3)
        }
        binding.btn4.setOnClickListener {
            bindMenu(MainMenuType.MENU4)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LAST_FRAGMENT_TAG, lastFragmentTag)
        super.onSaveInstanceState(outState)
    }

    private fun findMenuByTag(tag: String?): MainMenuType {
        if (tag == null) return MainMenuType.MENU1
        return MainMenuType.values()
            .firstOrNull {
                it.tag == tag
            } ?: MainMenuType.MENU1
    }

    private fun bindMenu(menu: MainMenuType) {
        val fragment = supportFragmentManager.findFragmentByTag(menu.tag) ?: when (menu) {
            MainMenuType.MENU1 -> MenuWithContainerFragment.newInstance("Menu1")
            MainMenuType.MENU2 -> MenuFragment.newInstance("Menu2")
            MainMenuType.MENU3 -> MenuFragment.newInstance("Menu3")
            MainMenuType.MENU4 -> MenuFragment.newInstance("Menu4")
        }
        bindAction(fragment, menu.tag)
    }

    private fun bindAction(fragment: Fragment, tag: String) {
        supportFragmentManager.showWithLifecycle(fragment, tag, lastFragmentTag)
        lastFragmentTag = tag
    }

    private companion object {
        private const val LAST_FRAGMENT_TAG = "LAST_FRAGMENT_TAG"
    }
}