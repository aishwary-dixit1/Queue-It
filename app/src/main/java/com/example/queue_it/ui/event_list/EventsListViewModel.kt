package com.example.queue_it.ui.event_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.queue_it.common.RequestStatus
import com.example.queue_it.model.Event
import com.example.queue_it.network.NetworkClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class EventsListViewModel(val category: String) : ViewModel() {
    val requestStatus = MutableStateFlow< RequestStatus<List<Event>>>(RequestStatus.Loading)

    init {
        loadEvents()
    }

    fun loadEvents() {
        viewModelScope.launch {
            val result = NetworkClient.getRunningEventsForCategory(category.uppercase())
            requestStatus.value = RequestStatus.Success(result)
        }
    }
}