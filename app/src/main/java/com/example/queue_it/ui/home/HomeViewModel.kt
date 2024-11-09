package com.example.queue_it.ui.home

import androidx.lifecycle.ViewModel
import com.example.queue_it.R
import com.example.queue_it.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Data class representing the state of the Home Screen UI
data class HomeScreenState(
    val isLoading: Boolean = false,            // Tracks if the data is currently loading
    val categories: List<Category> = emptyList(), // List of categories displayed on the screen
    val error: String? = null                  // Holds any error message if there's an issue loading data
)

// ViewModel for managing the UI state of the Home screen
class HomeViewModel : ViewModel() {
    // MutableStateFlow for internal state management
    private val _uiState = MutableStateFlow(HomeScreenState())

    // Public StateFlow that exposes the UI state to other composables
    val uiState: StateFlow<HomeScreenState> = _uiState

    init {
        loadCategories() // Load categories when ViewModel is initialized
    }

    // Private function to load predefined categories
    private fun loadCategories() {
        // List of predefined categories with IDs, names, and drawable resources
        val categories = listOf(
            Category(1, "Doctor", R.drawable.hospital),           // Category for Doctor queue
            Category(2, "Concert", R.drawable.concert_queue_image), // Category for Concert queue
            Category(3, "Restaurant", R.drawable.restaurant_queue_image), // Category for Restaurant queue
            Category(4, "Theme Park", R.drawable.theme_queue_image),      // Category for Theme Park queue
            Category(5, "Conference", R.drawable.concert_queue_image),    // Category for Conference queue
            Category(6, "Sports", R.drawable.sports)              // Category for Sports queue
        )

        // Update the UI state with the loaded categories and set loading to false
        _uiState.value = _uiState.value.copy(
            categories = categories, // Set categories in UI state
            isLoading = false        // Indicate loading is complete
        )
    }
}
