package com.example.queue_it.ui.event_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.queue_it.common.EventCard
import com.example.queue_it.common.LoadingScreen
import com.example.queue_it.common.RequestStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsListScreen(category: String, navigateBack: () -> Unit, modifier: Modifier = Modifier) {
    val viewModel = viewModel<EventsListViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return EventsListViewModel(category) as T
            }
        }
    )

    val requestStatus = viewModel.requestStatus.collectAsState().value

    when (requestStatus) {
        RequestStatus.Loading -> LoadingScreen()
        is RequestStatus.Success -> {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text("$category EVENTS") },
                        navigationIcon = {
                            IconButton(onClick = { navigateBack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    )
                },


            ) {

                if (requestStatus.data.isEmpty()) {
                    Text(
                        text = "No events found!",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                } else {
                    LazyColumn(
                        modifier = modifier.fillMaxSize().padding(it).padding(horizontal = 16.dp)
                    ) {
                        items(requestStatus.data) {
                            EventCard(it)
                        }
                    }
                }
            }
        }

        RequestStatus.Idle -> {}
        is RequestStatus.Error -> {}
    }
}