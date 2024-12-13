package com.example.queue_it.ui.event_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.queue_it.model.Event
import com.example.queue_it.model.EventCategory
import com.example.queue_it.model.Queue
import com.example.queue_it.network.NetworkClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventDetailsViewModel(val eventId: Int) : ViewModel() {
    private val _uiState = MutableStateFlow(EventDetailsUiState())
    val uiState: StateFlow<EventDetailsUiState> = _uiState.asStateFlow()

    init {
        getEventById()
    }

    fun getEventById() {
        viewModelScope.launch {
            val eventDetails = NetworkClient.getEventById(eventId)
            _uiState.update {
                it.copy(
                    event = eventDetails
                )
            }

            loadQueues()
        }
    }

    fun addQueueToEvent(title: String, maxLimit: Int, eventId: Int) {
        val queue = Queue(title = title, maxLimit = maxLimit)
        viewModelScope.launch {
            NetworkClient.addQueueToEvent(queue, eventId)
        }
    }

    fun loadQueues() {
        viewModelScope.launch {
            val eventQueues = NetworkClient.getQueuesForEvent(eventId)
            _uiState.update {
                it.copy(
                    eventQueues = eventQueues
                )
            }
        }
    }
}

data class EventDetailsUiState(
    val event: Event = Event(
        title = "",
        description = "",
        startTime = -1,
        endTime = -1,
        category = EventCategory.OTHER,
        waitTime = -1
    ),
    val eventQueues: List<Queue> = emptyList()
)