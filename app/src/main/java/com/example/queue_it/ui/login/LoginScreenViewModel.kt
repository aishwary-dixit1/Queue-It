package com.example.queue_it.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoginSuccessful: Boolean = false,
    val errorMessage: String? = null
)

class LoginScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun login() {
        viewModelScope.launch {
            // Add validation and authentication logic here
            if (_uiState.value.email.isEmpty() || _uiState.value.password.isEmpty()) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Email or password cannot be empty"
                )
            } else {
                // Perform login logic here
                _uiState.value = _uiState.value.copy(
                    isLoginSuccessful = true,
                    errorMessage = null
                )
            }
        }
    }
}
