package com.example.queue_it.ui.login

import android.content.Context
import android.util.Log
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

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoginSuccessful: Boolean = false,
    val errorMessage: String? = null,
)

class LoginScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    val requestStatus = MutableStateFlow<RequestStatus<Unit>>(RequestStatus.Idle)

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun login(context: Context, email: String, password: String) {
        viewModelScope.launch {
            try {
                requestStatus.value = RequestStatus.Loading
                Log.d("check", "inside vm")
                NetworkClient.loginRequest(context, User(-1, email, password, -999))
                requestStatus.value = RequestStatus.Success(Unit)
            } catch (e: Exception) {
                requestStatus.value = RequestStatus.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun setErrorMsg(message: String) {
        _uiState.update {
            it.copy(
                errorMessage = message
            )
        }
    }
}
