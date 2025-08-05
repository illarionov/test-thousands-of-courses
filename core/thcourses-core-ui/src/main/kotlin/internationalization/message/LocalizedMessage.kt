package com.example.thcourses.core.ui.internationalization.message

import androidx.annotation.StringRes

public data class LocalizedMessage(
    @param:StringRes val resourceId: Int,
    val args: List<Any> = emptyList(),
)
