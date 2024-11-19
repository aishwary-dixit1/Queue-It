package com.example.queue_it.ui.signup

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.queue_it.common.RequestStatus
import com.example.queue_it.model.User
import com.example.queue_it.network.NetworkClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SignUpUiState(
    val email: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isSignUpSuccessful: Boolean = false,
    val errorMessage: String? = null
)

class SignUpScreenViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()
    var requestStatus = MutableStateFlow<RequestStatus<Unit>>(RequestStatus.Idle)
    private set

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

    fun handleSignUp(
        context: Context,
    ) {
        if (validateDetails()) {
            viewModelScope.launch {
                try {
                    requestStatus.value = RequestStatus.Loading
                    val user = mapToUser()
                    NetworkClient.signUpRequest(context, user)
                    requestStatus.value = RequestStatus.Success(Unit)
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            errorMessage = "Error: ${e.message}"
                        )
                    }
                }
            }
        } else {
            _uiState.update {
                it.copy(
                    errorMessage = "Invalid Details"
                )
            }
        }
    }

    private fun mapToUser() = User(
        email = _uiState.value.email,
        password = _uiState.value.password,
        phoneNo = _uiState.value.phoneNumber.toLong()
    )

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return emailRegex.matches(email)
    }

    /*
        Returns true if details are correct
     */
    private fun validateDetails(): Boolean {
        return (_uiState.value.email.isNotEmpty()
                && _uiState.value.password.isNotEmpty()
                && _uiState.value.phoneNumber.isNotEmpty()
                && isValidEmail(_uiState.value.email)
                && _uiState.value.password == _uiState.value.confirmPassword)
    }
}
