package com.example.queue_it.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.queue_it.R
import com.example.queue_it.model.Event
import com.example.queue_it.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Sample data for preview
object SampleData {
    val sampleUser = User(
        id = 1,
        name = "Aish",
        email = "aish@example.com",
        password = "password123",
        phoneNumber = 6969696969,
        age = 20,
        gender = "Male",
        business_id = null,
        customer_id = null
    )
}

data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val age: Int = 0,
    val gender: String = "",
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ProfileScreenViewModel(
    private val initialUser: User? = null
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUserProfile()
    }

    fun updateProfile(user: User) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                // Keep the existing events when updating the profile
                val currentEvents = _uiState.value.events

                _uiState.value = ProfileUiState(
                    name = user.name,
                    email = user.email,
                    phoneNumber = user.phoneNumber.toString(),
                    age = user.age,
                    gender = user.gender,
                    events = currentEvents
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to update profile: ${e.message}"
                )
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                // Sample events data
                val sampleEvents = listOf(
                    Event(
                        id = 1,
                        openingTime = "",
                        time = "7:00AM",
                        closingTime = "",
                        eventCategory = "",
                        title = "Cold Play",
                        description = "Nach Gana",
                        avgWaitingTime = "",
                        date = "18th Dec",
                        venue = "IIIT Ranchi",
                        imageResId = R.drawable.concert_queue_image
                    ),
                    Event(
                        id = 1,
                        openingTime = "",
                        time = "10:00AM",
                        closingTime = "",
                        eventCategory = "",
                        title = "Art Exhibition",
                        description = "Mona Lisa khud Host Karegi",
                        avgWaitingTime = "",
                        date = "22nd Dec",
                        venue = "IIIT Ranchi",
                        imageResId = R.drawable.queue_image
                    )
                )

                initialUser?.let { user ->
                    _uiState.value = ProfileUiState(
                        name = user.name,
                        email = user.email,
                        phoneNumber = user.phoneNumber.toString(),
                        age = user.age,
                        gender = user.gender,
                        events = sampleEvents
                    )
                } ?: run {
                    _uiState.value = ProfileUiState(
                        name = "Aish",
                        email = "aish@example.com",
                        phoneNumber = "6969696969",
                        age = 20,
                        gender = "Male",
                        events = sampleEvents
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load profile: ${e.message}"
                )
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}