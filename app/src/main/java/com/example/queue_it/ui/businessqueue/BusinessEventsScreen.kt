package com.example.queue_it.ui.businessqueue

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.queue_it.common.EventCard
import com.example.queue_it.common.GradientFloatingActionButton
import com.example.queue_it.common.LoadingScreen
import com.example.queue_it.common.RequestStatus
import com.example.queue_it.model.Event

@Composable
fun BusinessEventsScreen(
    onBack: () -> Unit,
    navigateToCreateEventScreen: () -> Unit,
    navigateToEventDetailsScreen: (Int) -> Unit,
    viewModel: BusinessEventsViewModel = viewModel()
) {
    val requestStatus by viewModel.requestStatus.collectAsState()
    val context = LocalContext.current.applicationContext
    viewModel.loadData(context)
    when (requestStatus) {
        RequestStatus.Idle -> {}
        RequestStatus.Loading -> LoadingScreen()
        is RequestStatus.Success -> BusinessEventsScreenContent(
            onBack,
            { navigateToCreateEventScreen() },
            { navigateToEventDetailsScreen(it) },
            context,
            (requestStatus as RequestStatus.Success<List<Event>>).data,
        )

        is RequestStatus.Error -> {
            Box {
                Text("Error, ${(requestStatus as RequestStatus.Error).message}")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessEventsScreenContent(
    onBack: () -> Unit,
    navigateToCreateEventScreen: () -> Unit,
    navigateToEventDetailsScreen: (Int) -> Unit,
    context: Context,
    eventList: List<Event>,
    viewModel: BusinessEventsViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event Management") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                windowInsets = WindowInsets(0)
            )
        },
        floatingActionButton = {
            GradientFloatingActionButton(
                onClick = {
                    navigateToCreateEventScreen()
                    viewModel.loadData(context)
                },
                modifier = Modifier
            )
        },
        contentWindowInsets = WindowInsets(0)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(eventList) { event ->
                    EventCard(event, viewModel.thisBusiness.value?.address ?: "") { navigateToEventDetailsScreen(it) }
                }
            }
        }
    }
}
