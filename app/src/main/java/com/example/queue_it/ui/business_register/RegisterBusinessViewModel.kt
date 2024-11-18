package com.example.queue_it.ui.business_register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.queue_it.common.RequestStatus
import com.example.queue_it.model.Business
import com.example.queue_it.model.EventCategory
import com.example.queue_it.network.NetworkClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RegisterBusinessViewModel : ViewModel() {
    val requestStatus = MutableStateFlow<RequestStatus<String>>(RequestStatus.Idle)

    fun registerBusiness(context: Context, name: String, address: String, category: EventCategory) {
        viewModelScope.launch {
            try {
                requestStatus.value = RequestStatus.Loading
                val business = Business(name = name, address = address, category = category)
                val token = NetworkClient.registerBusiness(context, business)
                requestStatus.value = RequestStatus.Success(token)
            } catch (e: Exception) {
                requestStatus.value = RequestStatus.Error(e.message ?: "Unknown error")
            }
        }
    }
}