package com.example.queue_it.ui.businessqueue

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.queue_it.common.GradientFloatingActionButton
import com.example.queue_it.common.LoadingScreen
import com.example.queue_it.common.RequestStatus
import com.example.queue_it.model.Event
import com.example.queue_it.util.convertEpochToDateTime

@Composable
fun BusinessEventsScreen(
    onBack: () -> Unit,
    navigateToCreateEventScreen: () -> Unit,
    navigateToEventDetailsScreen: (Int) -> Unit,
    viewModel: BusinessEventsViewModel = viewModel()
) {
    val requestStatus by viewModel.requestStatus.collectAsState()
    val context = LocalContext.current.applicationContext
    viewModel.loadEvents(context)
    when (requestStatus) {
        RequestStatus.Idle -> {}
        RequestStatus.Loading -> LoadingScreen()
        is RequestStatus.Success -> BusinessEventsScreenContent(
            onBack,
            { navigateToCreateEventScreen() },
            { navigateToEventDetailsScreen(it) },
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
    eventList: List<Event>,
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
                    EventCard(event) { navigateToEventDetailsScreen(it) }
                }
            }
        }
    }
}

@Composable
fun EventCard(
    event: Event,
    navigateToEventDetailsScreen: (Int) -> Unit,
) {

    val start = convertEpochToDateTime(event.startTime)
    val end = convertEpochToDateTime(event.endTime)
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = {
            navigateToEventDetailsScreen(event.id)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF64B5F6)
                    )

                    Text(
                        text = event.category.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "📍 {event.venue}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "$start - $end",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "#45",
                    style = MaterialTheme.typography.displayMedium,
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF64B5F6)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Wait Time",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${event.waitTime}",
                        style = MaterialTheme.typography.displayMedium,
                        fontStyle = FontStyle.Italic,
                        color = Color(0xFF64B5F6)
                    )
                }
            }
        }
    }
}
