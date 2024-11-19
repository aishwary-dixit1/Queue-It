package com.example.queue_it.ui.queue_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.queue_it.model.Customer
import com.example.queue_it.model.Event
import com.example.queue_it.model.EventCategory
import com.example.queue_it.model.Queue
import com.example.queue_it.network.NetworkClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QueueDetailsViewModel(val queueId: Int) : ViewModel() {
    private val _uiState = MutableStateFlow(QueueDetailsUiState())
    val uiState: StateFlow<QueueDetailsUiState> = _uiState.asStateFlow()

    init {
        loadQueue()
    }

    fun loadQueue() {
        viewModelScope.launch {
            val queueDetails = NetworkClient.getQueueById(queueId)
            _uiState.update {
                it.copy(
                    queue = queueDetails
                )
            }

            loadEvent(queueDetails.eventId)
        }
    }

    fun loadEvent(eventId: Int) {
        viewModelScope.launch {
            val eventDetails = NetworkClient.getEventById(eventId)
            _uiState.update {
                it.copy(
                    event = eventDetails
                )
            }

            loadCustomers()
        }
    }

    fun loadCustomers() {
        viewModelScope.launch {
            val customers = NetworkClient.getCustomersForQueue(queueId)
            _uiState.update {
                it.copy(
                    customers = customers
                )
            }
        }
    }


    fun removeCustomer(queueId: Int, customerId: Int) {
        viewModelScope.launch {
            NetworkClient.removeCustomer(queueId, customerId)
            loadCustomers()
        }
    }
}

data class QueueDetailsUiState(
    val queue: Queue = Queue(title = "", maxLimit = 0),
    val event: Event = Event(
        title = "", startTime = 0, endTime = 0,
        description = "",
        category = EventCategory.ENTERTAINMENT,
        waitTime = -1
    ),
    val customers: List<Customer> = emptyList()
)