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

        lastFragmentTag = savedInstanceState?.getString(LAST_FRAGMENT_TAG)
        val menuType = findMenuByTag(lastFragmentTag)
        bindMenu(menuType)
        setUpViews()
    }

    private fun setUpViews() {
        binding.title.text = title

        binding.btn1.setOnClickListener {
            bindMenu(SubMenuType.Sub1)
        }
        binding.btn2.setOnClickListener {
            bindMenu(SubMenuType.Sub2)
        }
        binding.btn3.setOnClickListener {
            bindMenu(SubMenuType.Sub3)
        }
        binding.btn4.setOnClickListener {
            bindMenu(SubMenuType.Sub4)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LAST_FRAGMENT_TAG, lastFragmentTag)
        super.onSaveInstanceState(outState)
    }

    private fun findMenuByTag(tag: String?): SubMenuType {
        if (tag == null) return SubMenuType.Sub1
        return SubMenuType.values()
            .firstOrNull {
                it.tag == tag
            } ?: SubMenuType.Sub1
    }

    private fun bindMenu(type: SubMenuType) {
        val fragment = childFragmentManager.findFragmentByTag(type.tag) ?: when (type) {
            SubMenuType.Sub1 -> SubFragment.newInstance("Sub1", "FFFF0000".toLong(16).toInt())
            SubMenuType.Sub2 -> SubFragment.newInstance("Sub2", "FF1565C0".toLong(16).toInt())
            SubMenuType.Sub3 -> SubFragment.newInstance("Sub3", "FFFF6F00".toLong(16).toInt())
            SubMenuType.Sub4 -> SubFragment.newInstance("Sub4", "FF85BB5C".toLong(16).toInt())
        }
        bindAction(fragment, type.tag)
    }

    private fun bindAction(fragment: Fragment, tag: String) {
        childFragmentManager.showWithLifecycle(fragment, tag, lastFragmentTag)
        lastFragmentTag = tag
    }

    enum class SubMenuType {
        Sub1, Sub2, Sub3, Sub4;

        val tag: String
            get() = name.uppercase()
    }

    companion object {
        private const val LAST_FRAGMENT_TAG = "LAST_FRAGMENT_TAG"

        fun newInstance(title: String): MenuWithContainerFragment {
            return MenuWithContainerFragment().apply {
                arguments = bundleOf("title" to title)
            }
        }
    }
}