package com.example.thcourses.feature.home.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.thcourses.feature.home.impl.databinding.FragmentHomeBinding

internal class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInsets()
        setupListeners()
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.appbar) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout()
            )
            v.updatePadding(
                left = bars.left,
                top = bars.top,
                right = bars.right,
                bottom = 0,
            )
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun setupListeners() {
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
