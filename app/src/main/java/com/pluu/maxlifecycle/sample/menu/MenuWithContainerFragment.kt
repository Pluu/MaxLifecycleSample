package com.pluu.maxlifecycle.sample.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.pluu.maxlifecycle.sample.SampleFragment
import com.pluu.maxlifecycle.sample.databinding.FragmentMenuWithContainerBinding
import com.pluu.maxlifecycle.sample.sub.SubFragment
import com.pluu.maxlifecycle.showWithLifecycle

class MenuWithContainerFragment : SampleFragment() {
    override val key: String
        get() = title

    private val title: String by lazy {
        arguments?.getString("title").orEmpty()
    }

    private var _binding: FragmentMenuWithContainerBinding? = null
    private val binding: FragmentMenuWithContainerBinding get() = _binding!!

    private val screen1 by lazy {
        SubFragment.newInstance("Sub1", "FFFF0000".toLong(16).toInt())
    }
    private val screen2 by lazy {
        SubFragment.newInstance("Sub2", "FF1565C0".toLong(16).toInt())
    }
    private val screen3 by lazy {
        SubFragment.newInstance("Sub3", "FFFF6F00".toLong(16).toInt())
    }
    private val screen4 by lazy {
        SubFragment.newInstance("Sub4", "FF85BB5C".toLong(16).toInt())
    }

    private var lastFragmentTag: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuWithContainerBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = title

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
        lastFragmentTag = childFragmentManager.showWithLifecycle(fragment, lastFragmentTag)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(title: String): MenuWithContainerFragment {
            return MenuWithContainerFragment().apply {
                arguments = bundleOf("title" to title)
            }
        }
    }
}