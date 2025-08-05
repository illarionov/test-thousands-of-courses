package com.example.thcourses.feature.home.presentation.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil3.load
import com.example.thcourses.core.model.Rate
import com.example.thcourses.core.ui.internationalization.formatter.DateFormatter
import com.example.thcourses.core.ui.internationalization.formatter.RatingFormatter
import com.example.thcourses.core.ui.internationalization.message.getString
import com.example.thcourses.feature.home.impl.databinding.FragmentCourseBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class CourseFragment : Fragment() {
    private var _binding: FragmentCourseBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CourseViewModel by viewModels()
    private val ratingFormatter: RatingFormatter = RatingFormatter()
    private val dateFormatter: DateFormatter = DateFormatter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCourseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeUiState()
    }

    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.favoriteButton.setOnClickListener {
            viewModel.toggleFavorite()
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    when (state) {
                        is CourseUiState.Loading -> showLoading()
                        is CourseUiState.Success -> showContent(state)
                        is CourseUiState.Error -> showError(state)
                    }
                }
        }
    }

    private fun showLoading() {
    }

    private fun showContent(state: CourseUiState.Success) {
        with(binding) {
            courseImage.load(state.course.imageUrl)
            rating.text = ratingFormatter.format(state.course.rate)
            date.text = dateFormatter.format(state.course.startDate)
            title.text = state.course.title
            authorAvatar.load(state.course.author.avatarUrl)
            authorName.text = state.course.author.name
            description.text = state.course.text
            favoriteButton.isChecked = state.course.hasLike
        }
    }

    private fun showError(state: CourseUiState.Error) {
        Snackbar.make(
            binding.root,
            resources.getString(state.message),
            Snackbar.LENGTH_LONG,
        ).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
