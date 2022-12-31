package com.pluu.maxlifecycle.sample.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.pluu.maxlifecycle.sample.SampleFragment
import com.pluu.maxlifecycle.sample.databinding.FragmentMenuBinding

class MenuFragment : SampleFragment() {
    override val key: String
        get() = title

    private val title: String by lazy {
        arguments?.getString("title").orEmpty()
    }

    private var _binding: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = title
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(title: String): MenuFragment {
            return MenuFragment().apply {
                arguments = bundleOf("title" to title)
            }
        }
    }
}