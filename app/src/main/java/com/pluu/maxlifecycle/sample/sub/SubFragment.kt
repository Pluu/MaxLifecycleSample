package com.pluu.maxlifecycle.sample.sub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.pluu.maxlifecycle.sample.SampleFragment
import com.pluu.maxlifecycle.sample.databinding.FragmentSubBinding
import logcat.logcat

class SubFragment : SampleFragment() {

    override val key: String
        get() = title

    private var _binding: FragmentSubBinding? = null
    private val binding: FragmentSubBinding get() = _binding!!

    private val viewModel by viewModels<SubViewModel>()

    private val title: String by lazy {
        arguments?.getString("title").orEmpty()
    }
    private val color: Int by lazy {
        arguments?.getInt("color") ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubBinding.inflate(
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

        viewModel.test.observe(visibleLifecycleOwner) {
            logcat { "[$title] hidden=[$isHidden] viewLifecycleOwner received $it" }
            binding.tvReceived.text = "Received: $it"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(title: String, @ColorInt colorValue: Int): SubFragment {
            return SubFragment().apply {
                arguments = bundleOf(
                    "title" to title,
                    "color" to colorValue
                )
            }
        }
    }
}