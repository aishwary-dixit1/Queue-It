package com.example.queue_it.ui.businessqueue

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.queue_it.common.RequestStatus
import com.example.queue_it.model.Event
import com.example.queue_it.network.NetworkClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BusinessEventsViewModel : ViewModel() {
    val requestStatus = MutableStateFlow<RequestStatus<List<Event>>>(RequestStatus.Idle)

    fun loadEvents(context: Context) {
        viewModelScope.launch {
            try {
                requestStatus.value = RequestStatus.Loading
                val events = NetworkClient.loadEventsForBusiness(context)
                requestStatus.value = RequestStatus.Success(events)
            } catch (e: Exception) {
                requestStatus.value = RequestStatus.Error(e.message ?: "Unknown Exception")
            }
        }
    }
}