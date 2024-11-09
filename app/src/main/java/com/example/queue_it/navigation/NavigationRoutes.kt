package com.example.queue_it.navigation


data class Screen(
    val route: String,
    val title: String,
    val location: String
) {
    companion object {
        val Home = Screen(
            route = "home",
            title = "Home",
            location = "Home"
        )
        val Categories = Screen(
            route = "categories",
            title = "Categories",
            location = "Home > Categories"
        )
        val Doctor = Screen(
            route = "doctor",
            title = "Doctor",
            location = "Home > Categories > Doctor"
        )
        val Concert = Screen(
            route = "concert",
            title = "Concert",
            location = "Home > Categories > Concert"
        )
        val Restaurants = Screen(
            route = "restaurants",
            title = "Restaurants",
            location = "Home > Categories > Restaurants"
        )
        val ThemePark = Screen(
            route = "theme-park",
            title = "Theme Park",
            location = "Home > Categories > Theme Park"
        )
        val Sports = Screen(
            route = "sports",
            title = "Sports",
            location = "Home > Categories > Sports"
        )
        val Queues = Screen(
            route = "queue",
            title = "Queue",
            location = "Queue"
        )
        val Profile = Screen(
            route = "profile",
            title = "Profile",
            location = "Profile"
        )
        val Notification = Screen(
            route = "notification",
            title = "Notification",
            location = "Notification"
        )
    }
}

