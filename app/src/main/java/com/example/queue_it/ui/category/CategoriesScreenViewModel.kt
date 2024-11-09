package com.example.queue_it.ui.category
//
//import android.location.Location
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.DateRange
//import androidx.compose.material.icons.filled.Favorite
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.LocationOn
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material.icons.filled.Settings
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.lifecycle.ViewModel
//import com.example.queue_it.R
//import com.example.queue_it.model.Business
//import com.example.queue_it.model.Columns
//import com.example.queue_it.model.Event
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import java.sql.Timestamp
//import java.text.DateFormat
//
//class CategoriesScreenViewModel : ViewModel() {
//    private val _uiState = MutableStateFlow(CategoryUiState())
//    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()
//
//    init {
//        // Initialize with sample data
//        _uiState.value = CategoryUiState(
//            currentCategory = BusinessCategory(
//                title = "Concerts",
//                type = CategoryType.CONCERT,
//                eventCount = 12,
//                icon = Icons.Default.Favorite,
//                eventImages = List(9) { "https://example.com/event$it.jpg" }
//            ),
//            categories = listOf(
//                BusinessCategory(
//                    title = "Concerts",
//                    type = CategoryType.CONCERT,
//                    eventCount = 12,
//                    icon = Icons.Default.Favorite,
//                    eventImages = List(9) { "https://example.com/event$it.jpg" }
//                ),
//                BusinessCategory(
//                    title = "Restaurants",
//                    type = CategoryType.DINING,
//                    eventCount = 9,
//                    icon = Icons.Default.LocationOn,
//                    eventImages = List(6) { "https://example.com/event$it.jpg" }
//                )
//            ),
//            topEvents = listOf(
//                Event(
//                    id = 1,
//                    openingTime = Timestamp.now(),
//                    closingTime = Timestamp.now().plus(2, DateTimeUnit.HOUR),
//                    businessId = Business(
//                        id = 1,
//                        businessName = "Concert Hall",
//                        address = "123 Main St",
//                        businessCategory = "Concert",
//                        currentOpenEventsId = mutableListOf(this),
//                        historyOfQueuesId = mutableListOf()
//                    ),
//                    eventCategory = "Concert",
//                    columnsId = Columns(
//                        columnId = 1,
//                        columnTitle = "General Admission",
//                        maxLimit = 1000
//                    ),
//                    title = "Summer Music Festival",
//                    description = "Enjoy a day of live music and entertainment",
//                    avgWaitingTime = Timestamp.now().plus(30, DateTimeUnit.MINUTE),
//                    date = DateFormat.parse("2023-06-15"),
//                    venue = "Outdoor Amphitheater",
//                    imageResId = R.drawable.event_image
//                )
//            ),
//            selectedNavItem = NavItem.HOME
//        )
//    }
//
//    fun onNavItemSelected(item: NavItem) {
//        _uiState.value = _uiState.value.copy(selectedNavItem = item)
//    }
//}
//
//data class CategoryUiState(
//    val currentCategory: BusinessCategory? = null,
//    val categories: List<BusinessCategory> = emptyList(),
//    val topEvents: List<Event> = emptyList(),
//    val selectedNavItem: NavItem = NavItem.HOME
//)
//
//data class BusinessCategory(
//    val title: String,
//    val type: CategoryType,
//    val eventCount: Int,
//    val icon: ImageVector,
//    val eventImages: List<String>
//    val location: Location
//)
//
//enum class CategoryType {
//    CONCERT, DINING
//}
//
//enum class NavItem(
//    val title: String,
//    val icon: ImageVector
//) {
//    HOME("Home", Icons.Default.Home),
//    APPOINTMENT("Appoint", Icons.Default.DateRange),
//    SEARCH("Search", Icons.Default.Search),
//    SETTINGS("Settings", Icons.Default.Settings)
//}