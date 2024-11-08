package com.example.queue_it.ui.category

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

class CategoryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DoctorFinderUiState())
    val uiState: StateFlow<DoctorFinderUiState> = _uiState.asStateFlow()

    init {
        // Initialize with sample data
        _uiState.value = DoctorFinderUiState(
            userName = "Shahinur",
            userProfilePic = "https://example.com/profile.jpg",
            categories = listOf(
                DoctorCategory(
                    type = CategoryType.CARDIO,
                    name = "Cardio",
                    doctorCount = 12,
                    icon = Icons.Default.Favorite,
                    doctorAvatars = List(9) { "https://example.com/doctor$it.jpg" }
                ),
                DoctorCategory(
                    type = CategoryType.DENTAL,
                    name = "Dental",
                    doctorCount = 9,
                    icon = Icons.Default.LocationOn,
                    doctorAvatars = List(6) { "https://example.com/dentist$it.jpg" }
                )
            ),
            topDoctors = listOf(
                Doctor(
                    name = "Dr. Jenny Wilson",
                    hospital = "Bristol hospital",
                    specialty = "Cardiology",
                    availableTime = "4:30PM-7:30PM",
                    image = "https://example.com/dr_jenny.jpg"
                )
            )
        )
    }

    fun onNavItemSelected(item: NavItem) {
        _uiState.value = _uiState.value.copy(selectedNavItem = item)
    }
}

data class DoctorFinderUiState(
    val userName: String = "",
    val userProfilePic: String = "",
    val categories: List<DoctorCategory> = emptyList(),
    val topDoctors: List<Doctor> = emptyList(),
    val selectedNavItem: NavItem = NavItem.HOME
)

data class DoctorCategory(
    val type: CategoryType,
    val name: String,
    val doctorCount: Int,
    val icon: ImageVector,
    val doctorAvatars: List<String>
)

enum class CategoryType {
    CARDIO, DENTAL
}

data class Doctor(
    val name: String,
    val hospital: String,
    val specialty: String,
    val availableTime: String,
    val image: String
)

enum class NavItem(
    val title: String,
    val icon: ImageVector
) {
    HOME("Home", Icons.Default.Home),
    APPOINTMENT("Appoint", Icons.Default.DateRange),
    SEARCH("Search", Icons.Default.Search),
    SETTINGS("Settings", Icons.Default.Settings)
}