package com.example.thcourses.feature.home.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thcourses.core.model.CourseSortOrder
import com.example.thcourses.core.ui.internationalization.message.getString
import com.example.thcourses.core.ui.recyclerview.VerticalSpacingItemDecoration
import com.example.thcourses.feature.home.impl.R
import com.example.thcourses.feature.home.impl.databinding.FragmentHomeBinding
import com.example.thcourses.feature.home.presentation.shared.ui.createCourseListAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val coursesAdapter = createCourseListAdapter(
        onFavoriteClick = { courseId, isFavorite ->
            viewModel.setFavorite(courseId, isFavorite)
        },
        onDetailsClick = { courseId ->
            val bundle = bundleOf("courseId" to courseId.value)
            findNavController().navigate(R.id.action_home_to_course, bundle)
        },
    )

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
        setupRecyclerView()
        setupListeners()
        observeUiState()
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.appbar) { v, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout(),
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

    private fun setupListeners() {
        binding.filterButton.setOnClickListener {
            viewModel.toggleSortOrder()
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    when (state) {
                        is HomeUiState.Loading -> showLoading()
                        is HomeUiState.Success -> showContent(state)
                        is HomeUiState.Error -> showError(state)
                    }
                }
        }
    }

    private fun showLoading() {
        binding.cards.isVisible = false
    }

    private fun showContent(state: HomeUiState.Success) {
        binding.cards.isVisible = true
        coursesAdapter.items = state.courses

        binding.sortOrderIndicator.setText(
            when (state.sortOrder) {
                CourseSortOrder.UNSORTED -> R.string.sort_order_unsorted
                CourseSortOrder.PUBLISH_DATE -> R.string.sort_order_by_date
            },
        )
    }

    private fun showError(state: HomeUiState.Error) {
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
