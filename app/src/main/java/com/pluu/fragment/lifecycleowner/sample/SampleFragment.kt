package com.pluu.fragment.lifecycleowner.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.pluu.fragment.lifecycleowner.LazyLifecycleFragment
import com.pluu.fragment.lifecycleowner.sample.databinding.FragmentSampleBinding
import logcat.logcat

class SampleFragment : LazyLifecycleFragment() {

    private var _binding: FragmentSampleBinding? = null
    private val binding: FragmentSampleBinding get() = _binding!!

    private val viewModel by viewModels<SampleViewModel>()

    private val title: String by lazy {
        arguments?.getString("title").orEmpty()
    }
    private val color: Int by lazy {
        arguments?.getInt("color") ?: 0
    }

    override val lazyKey: String
        get() = title

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSampleBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvText.text = title
        binding.tvText.setTextColor(color)
        binding.btnButton.setOnClickListener {
            viewModel.load()
        }
        binding.btnStart.setOnClickListener {
            binding.customView.start(viewLifecycleOwner)
        }
        binding.btnStop.setOnClickListener {
            binding.customView.stop()
        }

        viewModel.test.observe(viewLifecycleOwner) {
            logcat { "[$title] hidden=[$isHidden] viewLifecycleOwner received $it" }
            binding.tvReceived.text = "Received: $it"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(title: String, @ColorInt colorValue: Int): SampleFragment {
            return SampleFragment().apply {
                arguments = bundleOf(
                    "title" to title,
                    "color" to colorValue
                )
            }
        }
    }
}