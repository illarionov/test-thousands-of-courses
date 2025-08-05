package com.example.thcourses.data.repository.courses.api.util

import com.slack.eithernet.ApiResult

// XXX: должно быть в более походящем модуле
public inline fun <T: Any, E: Any, R: Any> ApiResult<T, E>.mapSuccess(
    block: (T) -> R
): ApiResult<R, E> = when (this) {
    is ApiResult.Success<T> -> ApiResult.success(block(this.value))
    is ApiResult.Failure -> this
}

