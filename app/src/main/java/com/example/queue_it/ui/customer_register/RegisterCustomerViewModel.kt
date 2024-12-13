package com.example.queue_it.ui.customer_register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.queue_it.model.Customer
import com.example.queue_it.model.Gender
import com.example.queue_it.network.NetworkClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterCustomerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterCustomerUiState("", ""))
    val uiState: StateFlow<RegisterCustomerUiState> = _uiState.asStateFlow()

    fun onNameChange(newVal: String) {
        _uiState.update {
            it.copy(
                name = newVal
            )
        }
    }

    fun onAgeChange(newVal: String) {
        _uiState.update {
            it.copy(
                age = newVal
            )
        }
    }

    fun onGenderChange(newVal: Gender) {
        _uiState.update {
            it.copy(
                selected = newVal
            )
        }
    }

    fun registerCustomer(context: Context, customer: Customer) {
        viewModelScope.launch {
            NetworkClient.registerCustomer(context, customer)
        }
    }
}

data class RegisterCustomerUiState(
    val name: String,
    val age: String,
    val selected: Gender = Gender.OTHER,
    val errorMsg: String? = null
)