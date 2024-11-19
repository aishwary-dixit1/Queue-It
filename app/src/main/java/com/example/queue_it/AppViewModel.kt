package com.example.queue_it

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.queue_it.local.LocalStorage
import com.example.queue_it.navigation.Screen
import kotlinx.coroutines.flow.first

class AppViewModel : ViewModel() {
    val startDestination = mutableStateOf(Screen.Onboarding.route)

    suspend fun decideStartDestination(context: Context): String {
        val user = LocalStorage.getUserToken(context).first()
        return if (user == "none") Screen.Onboarding.route else Screen.Home.route
    }

}