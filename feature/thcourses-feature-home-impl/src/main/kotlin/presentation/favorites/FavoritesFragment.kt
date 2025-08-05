package com.example.thcourses.feature.home.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thcourses.core.ui.internationalization.message.getString
import com.example.thcourses.core.ui.recyclerview.VerticalSpacingItemDecoration
import com.example.thcourses.feature.home.impl.R
import com.example.thcourses.feature.home.impl.databinding.FragmentFavoritesBinding
import com.example.thcourses.feature.home.presentation.shared.ui.createCourseListAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()
    private val coursesAdapter = createCourseListAdapter(
        onFavoriteClick = { courseId, _ ->
            viewModel.removeFromFavorite(courseId)
        },
        onDetailsClick = { courseId ->
            val bundle = bundleOf("courseId" to courseId.value)
            findNavController().navigate(R.id.action_favorites_to_course, bundle)
        },
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeUiState()
    }

    private fun setupRecyclerView() {
        binding.cards.apply {
            adapter = coursesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                VerticalSpacingItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.course_item_spacing),
                ),
            )
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is FavoritesUiState.Loading -> showLoading()
                        is FavoritesUiState.Success -> showContent(state)
                        is FavoritesUiState.Error -> showError(state)
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.cards.isVisible = false
    }

    private fun showContent(state: FavoritesUiState.Success) {
        binding.cards.isVisible = true
        coursesAdapter.items = state.courses
    }

    private fun showError(state: FavoritesUiState.Error) {
        binding.cards.isVisible = false
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
