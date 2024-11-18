package com.example.queue_it.ui.queue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.queue_it.common.RequestStatus
import com.example.queue_it.model.Event
import com.example.queue_it.network.NetworkClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class QueueScreenViewModel : ViewModel() {

    val requestStatus = MutableStateFlow<RequestStatus<List<Event>>>(RequestStatus.Loading)

    private val _entertainmentSections = MutableStateFlow<List<EntertainmentSection>>(emptyList())
    val entertainmentSections: StateFlow<List<EntertainmentSection>> = _entertainmentSections.asStateFlow()

    init {
        loadActiveQueues()
        loadEntertainmentSections()
    }

    fun loadActiveQueues() {
        viewModelScope.launch {
            try {
                requestStatus.value = RequestStatus.Loading
                val queues = NetworkClient.getAllRunningEvents()
                requestStatus.value = RequestStatus.Success(queues)
            } catch (e: Exception) {
                requestStatus.value = RequestStatus.Error(e.message ?: "Unknown error")
            }
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

    fun mapUnixToDateTime(timestamp: Long) : LocalDateTime {
        val zoneId = ZoneId.systemDefault()  // Use the system's default timezone

        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), zoneId)
        return dateTime
    }
}

data class EntertainmentSection(
    val title: String,
    val items: List<String>
)

