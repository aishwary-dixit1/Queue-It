package com.example.queue_it.ui.queue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.queue_it.R
import com.example.queue_it.model.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QueueScreenViewModel : ViewModel() {
    private val _activeQueues = MutableStateFlow<List<QueueStatus>>(emptyList())
    val activeQueues: StateFlow<List<QueueStatus>> = _activeQueues.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _entertainmentSections = MutableStateFlow<List<EntertainmentSection>>(emptyList())
    val entertainmentSections: StateFlow<List<EntertainmentSection>> = _entertainmentSections.asStateFlow()

    init {
        loadActiveQueues()
        loadEntertainmentSections()
    }

    private fun loadActiveQueues() {
        viewModelScope.launch {
            // Simulate API call/Database fetch
            //replace with actual data fetching
            _isLoading.value = true
            delay(1000) // Simulate network delay

            // replace with real data fetch
            _activeQueues.value = listOf(
                QueueStatus(
                    event = Event(
                        id = 1,
                        openingTime = "09:00 AM",
                        time = "10:00 AM",
                        closingTime = "06:00 PM",
                        eventCategory = "Concert",
                        title = "Summer Music Festival",
                        description = "Annual music festival",
                        avgWaitingTime = "45 mins",
                        date = "2024-04-15",
                        venue = "Central Park",
                        imageResId = R.drawable.concert_queue_image,
                        businessId = null,
                        columnsId = null,
                    ),
                    tokenNumber = "A123",
                    position = 5,
                    peopleAhead = 4,
                    expectedWaitTime = "20 min",
                    expectedTurnTime = "11:20 AM"
                )
            )
            _isLoading.value = false
        }
    }

    private fun loadEntertainmentSections() {
        _entertainmentSections.value = listOf(
            EntertainmentSection("Games", listOf("Puzzle", "Quiz", "Word Game")),
            EntertainmentSection("Videos", listOf("Short Films", "Music Videos", "Comedy Clips")),
            EntertainmentSection("Music", listOf("Top Charts", "Podcasts", "Radio")),
            EntertainmentSection("Reading", listOf("News", "Articles", "Stories"))
        )
    }
}

// Data classes for UI state
data class QueueStatus(
    val event: Event,
    val tokenNumber: String,
    val position: Int,
    val peopleAhead: Int,
    val expectedWaitTime: String,
    val expectedTurnTime: String
)

data class EntertainmentSection(
    val title: String,
    val items: List<String>
)

