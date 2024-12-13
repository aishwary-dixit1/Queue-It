package com.example.queue_it.ui.businessqueue

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.queue_it.common.RequestStatus
import com.example.queue_it.model.Business
import com.example.queue_it.model.Event
import com.example.queue_it.network.NetworkClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BusinessEventsViewModel : ViewModel() {
    val requestStatus = MutableStateFlow<RequestStatus<List<Event>>>(RequestStatus.Idle)
    val thisBusiness = MutableStateFlow<Business?>(null)

    fun loadData(context: Context) {
        viewModelScope.launch {
            try {
                requestStatus.value = RequestStatus.Loading
                loadBusiness(context)
                val events = loadEvents(context)
                requestStatus.value = RequestStatus.Success(events)
            } catch (e: Exception) {
                requestStatus.value = RequestStatus.Error(e.message ?: "Unknown Exception")
            }
        }
    }

    private suspend fun loadEvents(context: Context): List<Event> {
        return NetworkClient.loadEventsForBusiness(context)

    }

    private suspend fun loadBusiness(context: Context) {
        try {
            val business = NetworkClient.getThisBusiness(context)
            thisBusiness.value = business
        } catch (e: Exception) {
            requestStatus.value = RequestStatus.Error(e.message ?: "Unknown Exception")
        }
    }
}