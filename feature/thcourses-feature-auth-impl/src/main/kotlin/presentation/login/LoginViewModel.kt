package com.example.thcourses.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor() : ViewModel() {
    private val _events = Channel<Label>(
        capacity = Channel.UNLIMITED,
    )
    val events: Flow<Label> get() = _events.receiveAsFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    val isLoginButtonEnabled: StateFlow<Boolean> = combine(email, password) { email, password ->
        isLoginFormValid(email, password)
    }
        .stateIn(
            viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = false,
        )

    fun onEmailChanged(input: String) {
        _email.value = input
    }

    fun onPasswordChanged(input: String) {
        _password.value = input
    }

    fun onLoginClicked() {
        if (isLoginFormValid(email.value, password.value)) {
            _events.trySend(Label.LoginSuccess)
        }
    }

    internal sealed interface Label {
        data object LoginSuccess : Label
    }

    private companion object {
        private val emailPattern = """^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z0-9]+$""".toRegex()

        fun isLoginFormValid(
            email: String,
            password: String,
        ): Boolean {
            return when {
                password.isEmpty() -> false
                email.isBlank() -> false
                !email.matches(emailPattern) -> false
                else -> true
            }
        }
    }
}
