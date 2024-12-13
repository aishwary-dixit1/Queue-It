package com.example.queue_it.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.queue_it.common.RequestStatus
import com.example.queue_it.local.LocalStorage
import com.example.queue_it.model.Customer
import com.example.queue_it.model.Gender
import com.example.queue_it.model.User
import com.example.queue_it.network.NetworkClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    val requestStatus = MutableStateFlow<RequestStatus<Unit>>(RequestStatus.Loading)

    fun loadUser(context: Context) {
        viewModelScope.launch {
            val thisUser = NetworkClient.getThisUser(context)
            _uiState.update {
                it.copy(
                    user = thisUser
                )
            }

            val thisCustomer = NetworkClient.getThisCustomer(context)
            _uiState.update {
                it.copy(
                    customer = thisCustomer
                )
            }

            requestStatus.value = RequestStatus.Success(Unit)
        }
    }

    fun signOut(context: Context) {
        viewModelScope.launch {
            LocalStorage.saveUserToken(context, "none")
            LocalStorage.saveBusinessToken(context, "none")
            LocalStorage.saveCustomerToken(context, "none")
        }
    }
}

data class ProfileUiState(
    val user: User = User(
        email = "",
        password = "",
        phoneNo = -1
    ),

    val customer: Customer = Customer(
        name = "",
        age = -1,
        gender = Gender.OTHER
    )
)