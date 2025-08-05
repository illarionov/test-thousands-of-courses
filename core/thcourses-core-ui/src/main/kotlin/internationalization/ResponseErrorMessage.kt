package com.example.thcourses.core.ui.internationalization

import com.example.thcourses.core.ui.R
import com.example.thcourses.core.ui.internationalization.message.LocalizedMessage
import com.slack.eithernet.ApiResult
import java.net.SocketException
import java.net.UnknownHostException

public fun ApiResult.Failure<Unit>.getCommonErrorMessage(): LocalizedMessage = when (this) {
    is ApiResult.Failure.ApiFailure<*> -> LocalizedMessage(R.string.response_error_api_failure)
    is ApiResult.Failure.HttpFailure<*> -> when (this.code) {
        400 -> LocalizedMessage(R.string.response_error_user_input_error)
        401 -> LocalizedMessage(R.string.response_error_user_unauthorized)
        404 -> LocalizedMessage(R.string.response_error_user_not_found)
        in 400..499 -> LocalizedMessage(
            R.string.response_error_authorization_error,
            listOf(this.code),
        )
        else -> LocalizedMessage(R.string.response_error_unknown_http_code_failure, listOf(this.code))
    }
    is ApiResult.Failure.NetworkFailure -> when {
        this.error is UnknownHostException
                || this.error is SocketException -> LocalizedMessage(R.string.response_error_no_internet_connection)
        else -> LocalizedMessage(R.string.response_error_network_failure)
    }
    is ApiResult.Failure.UnknownFailure -> LocalizedMessage(R.string.response_error_unknown_failure)
}
