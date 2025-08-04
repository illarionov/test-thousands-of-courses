package com.example.thcourses.feature.auth.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thcourses.core.ui.util.SingleLiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class LoginViewModel() : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = SingleLiveEvent<String>()
    val error: LiveData<String> = _error

    private val _success = SingleLiveEvent<Unit>()
    val success: LiveData<Unit> = _success

    private var email = ""
    private var password = ""
    private var confirmPassword = ""

    fun onEmailChanged(value: String) {
        email = value
    }

    fun onPasswordChanged(value: String) {
        password = value
    }

    fun onConfirmPasswordChanged(value: String) {
        confirmPassword = value
    }

    fun login() {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                when {
                    email.isBlank() -> _error.value = "Email не может быть пустым"
                    !email.isValidEmail() -> _error.value = "Некорректный email"
                    password.isBlank() -> _error.value = "Пароль не может быть пустым"
                    password.length < 6 -> _error.value = "Пароль должен быть не менее 6 символов"
                    password != confirmPassword -> _error.value = "Пароли не совпадают"
                    else -> {
                        delay(1000) // Имитация запроса
                        _success.value = Unit
                    }
                }
            } catch (e: Exception) {
                _error.value = "Ошибка регистрации"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun String.isValidEmail() =
        android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
