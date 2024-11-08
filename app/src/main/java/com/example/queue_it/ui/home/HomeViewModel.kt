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
    

    init {
        loadCategories()
    }

    private fun loadCategories() {
        val categories = listOf(
            Category(1, "Doctor", R.drawable.hospital),
            Category(2, "Concert", R.drawable.concert_queue_image),
            Category(3, "Restaurant", R.drawable.restaurant_queue_image),
            Category(4, "Theme Park", R.drawable.theme_queue_image),
            Category(5, "Conference", R.drawable.concert_queue_image),
            Category(6, "Sports", R.drawable.sports)
        )

        _uiState.value = _uiState.value.copy(
            categories = categories,
            isLoading = false
        )
    }

    fun onItemSelected(index: Int) {
        _selectedItemIndex.value = index
    }

}
