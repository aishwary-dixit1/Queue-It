package com.example.queue_it.ui.home
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.queue_it.R
import com.example.queue_it.model.Category
import com.example.queue_it.model.EventDetails
import com.example.queue_it.model.NavigationItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeScreenState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val error: String? = null
)

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState

    private val _selectedItemIndex = MutableStateFlow(0)
    val selectedItemIndex: StateFlow<Int> = _selectedItemIndex.asStateFlow()

    val navigationItems = listOf(
        NavigationItem(icon = Icons.Default.Home, label = "Home"),
        NavigationItem(icon = Icons.Default.Search, label = "Search"),
        NavigationItem(icon = Icons.Default.Person, label = "Profile")
    )

    val laughterTherapyEvents = listOf(
        EventDetails(
            title = "Kisi Ko Batana Mat",
            dateRange = "Sat, 11 Jan onwards",
            venue = "Sri Shanmukhananda Fine Arts & Sangeetha",
            imageResId = R.drawable.queue
        ),
        EventDetails(
            title = "Banker OP in the House",
            dateRange = "Fri, 15 Nov onwards",
            venue = "Flat Feet Studio: Mumbai",
            imageResId = R.drawable.queue
        ),
        EventDetails(
            title = "Abhishek Upmanyu LIVE",
            dateRange = "Fri, 15 Nov onwards",
            venue = "Multiple Venues",
            imageResId = R.drawable.queue
        )
    )

    val musicConcertEvents = listOf(
        EventDetails(
            title = "Classical Music Concert",
            dateRange = "Sat, 18 Feb onwards",
            venue = "Shanmukhananda Hall",
            imageResId = R.drawable.queue
        ),
        EventDetails(
            title = "Rock Music Festival",
            dateRange = "Fri, 24 Mar - Sun, 26 Mar",
            venue = "Dome, NSCI, Mumbai",
            imageResId = R.drawable.queue
        ),
        EventDetails(
            title = "Jazz Fusion Show",
            dateRange = "Thu, 6 Apr - Sat, 8 Apr",
            venue = "St. Andrews Auditorium",
            imageResId = R.drawable.queue
        )
    )
    init {
        loadCategories()
    }

    private fun loadCategories() {
        val categories = listOf(
            Category(1, "Doctor", R.drawable.queue),
            Category(2, "Concert", R.drawable.queue),
            Category(3, "Restaurant", R.drawable.queue),
            Category(4, "Theme Park", R.drawable.queue),
            Category(5, "Conference", R.drawable.queue),
            Category(6, "Sports", R.drawable.queue)
        )

        _uiState.value = _uiState.value.copy(
            categories = categories,
            isLoading = false
        )
    }

    fun onItemSelected(index: Int) {
        _selectedItemIndex.value = index
    }

    fun onCategoryClick(categoryId: Int) {
        // Handle category selection
    }

    fun onGetStartedClick() {
        // Handle get started button click
    }
}
