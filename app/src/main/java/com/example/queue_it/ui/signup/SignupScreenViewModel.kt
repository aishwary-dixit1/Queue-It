package com.example.queue_it.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isSignUpSuccessful: Boolean = false,
    val errorMessage: String? = null
)

class SignUpScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState

    fun onNameChange(newName: String) {
        _uiState.value = _uiState.value.copy(name = newName)
    }

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun onPhoneNumberChange(newPhoneNumber: String) {
        _uiState.value = _uiState.value.copy(phoneNumber = newPhoneNumber)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = newConfirmPassword)
    }

    fun signUp() {
        viewModelScope.launch {
            // Add validation logic here
            if (_uiState.value.password != _uiState.value.confirmPassword) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Passwords do not match"
                )
            } else {
                // Perform sign-up logic here
                _uiState.value = _uiState.value.copy(
                    isSignUpSuccessful = true,
                    errorMessage = null
                )
            }
        }
    }
}
