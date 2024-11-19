package com.example.queue_it.ui.add_event

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.queue_it.model.Event
import com.example.queue_it.network.NetworkClient
import kotlinx.coroutines.launch

class AddEventViewModel : ViewModel() {

    fun addEvent(context: Context, event: Event) {
        viewModelScope.launch {
            NetworkClient.createEvent(context, event)
        }
    }
}