package com.example.queue_it.ui.businessqueue


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.queue_it.model.Business
import com.example.queue_it.model.Columns
import com.example.queue_it.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class BusinessQueueState(
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

open class BusinessQueueViewModel : ViewModel() {
    val _state = MutableStateFlow(BusinessQueueState())
    val state: StateFlow<BusinessQueueState> = _state.asStateFlow()

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                // repository call
                _state.update { it.copy(events = getSampleEvents(), isLoading = false) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to load events: ${e.message}"
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addEvent(
        title: String,
        description: String,
        category: String,
        venue: String,
        time: String
    ) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val newEvent = Event(
                    id = (_state.value.events.maxOfOrNull { it.id } ?: 0) + 1,
                    openingTime = "09:00",
                    time = time,
                    closingTime = "17:00",
                    businessId = null,
                    eventCategory = category,
                    columnsId = Columns(1, "General", 100),
                    title = title,
                    description = description,
                    avgWaitingTime = "15 min",
                    date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    venue = venue,
                    imageResId = 0
                )
                _state.update { state ->
                    state.copy(
                        events = state.events + newEvent,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to add event: ${e.message}"
                    )
                }
            }
        }
    }

    fun editEvent(event: Event) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                _state.update { state ->
                    state.copy(
                        events = state.events.map { if (it.id == event.id) event else it },
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to edit event: ${e.message}"
                    )
                }
            }
        }
    }

    fun deleteEvent(event: Event, reason: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                _state.update { state ->
                    state.copy(
                        events = state.events.filter { it.id != event.id },
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to delete event: ${e.message}"
                    )
                }
            }
        }
    }
}

fun getSampleEvents(): List<Event> {
    val sampleBusiness = Business(
        id = 1,
        businessName = "Tech Conference Center",
        address = "123 Innovation Street",
        businessCategory = "Events",
        currentOpenEventsId = mutableListOf(),
        historyOfQueuesId = mutableListOf()
    )

    return listOf(
        Event(
            id = 1,
            openingTime = "09:00",
            time = "10:00",
            closingTime = "17:00",
            businessId = sampleBusiness,
            eventCategory = "Conference",
            columnsId = Columns(1, "General Entry", 200),
            title = "Tech Summit 2024",
            description = "Annual technology conference featuring latest innovations",
            avgWaitingTime = "20 min",
            date = "2024-11-15",
            venue = "Main Hall",
            imageResId = 0
        ),
        Event(
            id = 2,
            openingTime = "08:00",
            time = "09:00",
            closingTime = "16:00",
            businessId = sampleBusiness,
            eventCategory = "Workshop",
            columnsId = Columns(2, "Workshop Area", 50),
            title = "Mobile Dev Workshop",
            description = "Hands-on mobile development workshop",
            avgWaitingTime = "15 min",
            date = "2024-11-16",
            venue = "Workshop Room A",
            imageResId = 0
        ),
        Event(
            id = 3,
            openingTime = "13:00",
            time = "14:00",
            closingTime = "20:00",
            businessId = sampleBusiness,
            eventCategory = "Networking",
            columnsId = Columns(3, "Networking Area", 100),
            title = "Tech Mixer",
            description = "Evening networking event for tech professionals",
            avgWaitingTime = "10 min",
            date = "2024-11-17",
            venue = "Networking Lounge",
            imageResId = 0
        )
    )
}
