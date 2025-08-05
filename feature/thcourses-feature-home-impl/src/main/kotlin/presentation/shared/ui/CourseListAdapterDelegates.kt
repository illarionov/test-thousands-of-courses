package com.example.thcourses.feature.home.presentation.shared.ui

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.error
import coil3.request.placeholder
import com.example.thcourses.core.model.CourseId
import com.example.thcourses.core.ui.internationalization.formatter.DateFormatter
import com.example.thcourses.core.ui.internationalization.formatter.PriceFormatter
import com.example.thcourses.core.ui.internationalization.formatter.RatingFormatter
import com.example.thcourses.feature.home.impl.R
import com.example.thcourses.feature.home.impl.databinding.LayoutRvCourseCardBinding
import com.example.thcourses.feature.home.presentation.shared.model.CourseListItem
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

internal fun createCourseListAdapter(
    onFavoriteClick: (CourseId, Boolean) -> Unit,
    onDetailsClick: (CourseId) -> Unit,
    priceFormatter: PriceFormatter = PriceFormatter(),
    ratingFormatter: RatingFormatter = RatingFormatter(),
    dateFormatter: DateFormatter = DateFormatter(),
) = AsyncListDifferDelegationAdapter(
    CourseDiffCallback,
    courseListAdapterDelegate(onFavoriteClick, onDetailsClick, priceFormatter, ratingFormatter, dateFormatter),
)

private object CourseDiffCallback : DiffUtil.ItemCallback<CourseListItem>() {
    override fun areItemsTheSame(oldItem: CourseListItem, newItem: CourseListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CourseListItem, newItem: CourseListItem): Boolean {
        return oldItem == newItem
    }
}

internal fun courseListAdapterDelegate(
    onFavoriteClick: (CourseId, Boolean) -> Unit,
    onDetailsClick: (CourseId) -> Unit,
    priceFormatter: PriceFormatter = PriceFormatter(),
    ratingFormatter: RatingFormatter = RatingFormatter(),
    dateFormatter: DateFormatter = DateFormatter(),
) = adapterDelegateViewBinding<CourseListItem, CourseListItem, LayoutRvCourseCardBinding>(
    { layoutInflater, root -> LayoutRvCourseCardBinding.inflate(layoutInflater, root, false) },
) {
    binding.favoriteButton.setOnClickListener {
        onFavoriteClick(item.id, !item.hasLike)
    }
    binding.detailsButton.setOnClickListener {
        onDetailsClick(item.id)
    }

    bind {
        with(binding) {
            title.text = item.title
            description.text = item.text
            price.text = priceFormatter.format(item.price)
            date.text = dateFormatter.format(item.startDate)
            rating.text = ratingFormatter.format(item.rate)
            favoriteButton.isChecked = item.hasLike
            imageCover.load(item.imageUrl) {
                placeholder(R.drawable.img_placeholder)
                error(R.drawable.img_error)
            }
        }
    }
}
