package com.example.queue_it.ui.businessqueue

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.queue_it.commonUI.GradientFloatingActionButton
import com.example.queue_it.model.Event
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessQueueManagementScreen(
    viewModel: BusinessQueueViewModel,
    navController : NavController
) {
    val state by viewModel.state.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var eventToDelete by remember { mutableStateOf<Event?>(null) }
    var eventToEdit by remember { mutableStateOf<Event?>(null) }
    var showAddEventDialog by remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event Management") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
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
                onClick = { showAddEventDialog = true },
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
                items(
                    items = state.events,
                    key = { it.id }
                ) { event ->
//                    EventCard(
//                        event = event,
//                        onEdit = { viewModel.editEvent(it) },
//                        onDelete = {
//                            eventToDelete = it
//                            showDeleteDialog = true
//                        }
//                    )
                    EventCard(
                        event = event,
                        onEdit = {
                            eventToEdit = it
                            showEditDialog = true
                        },
                        onDelete = {
                            eventToDelete = it
                            showDeleteDialog = true
                        }
                    )
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
    if (showEditDialog && eventToEdit != null) {
        EditEventDialog(
            event = eventToEdit!!,
            onDismiss = {
                showEditDialog = false
                eventToEdit = null
            },
            onConfirm = { updatedEvent ->
                viewModel.editEvent(updatedEvent)
                showEditDialog = false
                eventToEdit = null
            }
        )
    }

    if (showDeleteDialog && eventToDelete != null) {
        DeleteEventDialog(
            event = eventToDelete!!,
            onDismiss = {
                showDeleteDialog = false
                eventToDelete = null
            },
            onConfirm = { event, reason ->
                viewModel.deleteEvent(event, reason)
                showDeleteDialog = false
                eventToDelete = null
            }
        )
    }

    if (showAddEventDialog) {
        AddEventDialog(
            onDismiss = { showAddEventDialog = false },
            onConfirm = { title, description, category, venue, time ->
                viewModel.addEvent(title, description, category, venue, time)
                showAddEventDialog = false
            }
        )
    }
}

@Composable
fun EventCard(
    event: Event,
    onEdit: (Event) -> Unit,
    onDelete: (Event) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                Column {
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF64B5F6)
                    )
                    Text(
                        text = event.eventCategory,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Row {
                    IconButton(onClick = { onEdit(event) }) {
                        Icon(Icons.Default.Edit, "Edit")
                    }
                    IconButton(onClick = { onDelete(event) }) {
                        Icon(Icons.Default.Delete, "Delete")
                    }
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "📍 ${event.venue}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "⏰ ${event.time}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Avg. Wait: ${event.avgWaitingTime}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Date: ${event.date}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
@Composable
fun EditEventDialog(
    event: Event,
    onDismiss: () -> Unit,
    onConfirm: (Event) -> Unit
) {
    var title by remember { mutableStateOf(event.title) }
    var description by remember { mutableStateOf(event.description) }
    var category by remember { mutableStateOf(event.eventCategory) }
    var venue by remember { mutableStateOf(event.venue) }
    var time by remember { mutableStateOf(event.time) }
    var showError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Event") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && title.isEmpty()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && description.isEmpty()
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && category.isEmpty()
                )

                OutlinedTextField(
                    value = venue,
                    onValueChange = { venue = it },
                    label = { Text("Venue") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && venue.isEmpty()
                )

                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Time (HH:mm)") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && time.isEmpty()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isEmpty() || description.isEmpty() ||
                        category.isEmpty() || venue.isEmpty() || time.isEmpty()
                    ) {
                        showError = true
                    } else {
                        onConfirm(event.copy(
                            title = title,
                            description = description,
                            eventCategory = category,
                            venue = venue,
                            time = time
                        ))
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
@Composable
fun DeleteEventDialog(
    event: Event,
    onDismiss: () -> Unit,
    onConfirm: (Event, String) -> Unit
) {
    var reason by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete Event") },
        text = {
            Column {
                Text("Are you sure you want to delete '${event.title}'?")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = reason,
                    onValueChange = {
                        reason = it
                        showError = false
                    },
                    label = { Text("Reason for deletion") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError
                )
                if (showError) {
                    Text(
                        text = "Please provide a valid reason",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (reason.length < 10) {
                        showError = true
                    } else {
                        onConfirm(event, reason)
                    }
                }
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun AddEventDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var venue by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create New Event") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && title.isEmpty()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && description.isEmpty()
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && category.isEmpty()
                )

                OutlinedTextField(
                    value = venue,
                    onValueChange = { venue = it },
                    label = { Text("Venue") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && venue.isEmpty()
                )

                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Time (HH:mm)") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && time.isEmpty()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isEmpty() || description.isEmpty() ||
                        category.isEmpty() || venue.isEmpty() || time.isEmpty()
                    ) {
                        showError = true
                    } else {
                        onConfirm(title, description, category, venue, time)
                    }
                }
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "Business Queue Management Full Screen",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BusinessQueueManagementPreview() {
    val previewViewModel = object : BusinessQueueViewModel() {
        init {
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        events = getSampleEvents(),
                        isLoading = false
                    )
                }
            }
        }
    }

    MaterialTheme {
        Surface {
            BusinessQueueManagementScreen(
                viewModel = previewViewModel,
                navController = rememberNavController()
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "Business Queue Management - Loading",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BusinessQueueManagementLoadingPreview() {
    val previewViewModel = object : BusinessQueueViewModel() {
        init {
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        events = emptyList(),
                        isLoading = true
                    )
                }
            }
        }
    }

    MaterialTheme {
        Surface {
            BusinessQueueManagementScreen(
                viewModel = previewViewModel,
                navController = rememberNavController()
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(
    name = "Business Queue Management - Empty",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BusinessQueueManagementEmptyPreview() {
    val previewViewModel = object : BusinessQueueViewModel() {
        init {
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        events = emptyList(),
                        isLoading = false
                    )
                }
            }
        }
    }

    MaterialTheme {
        Surface {
            BusinessQueueManagementScreen(
                viewModel = previewViewModel,
                navController = rememberNavController()
            )
        }
    }
}

@Preview(
    name = "Event Card",
    showBackground = true
)
@Composable
fun EventCardPreview() {
    val sampleEvent = getSampleEvents().first()

    MaterialTheme {
        Surface {
            EventCard(
                event = sampleEvent,
                onEdit = {},
                onDelete = {}
            )
        }
    }
}

@Preview(
    name = "Delete Event Dialog",
    showBackground = true
)
@Composable
fun DeleteEventDialogPreview() {
    val sampleEvent = getSampleEvents().first()

    MaterialTheme {
        Surface {
            DeleteEventDialog(
                event = sampleEvent,
                onDismiss = {},
                onConfirm = { _, _ -> }
            )
        }
    }
}

@Preview(
    name = "Add Event Dialog",
    showBackground = true
)
@Composable
fun AddEventDialogPreview() {
    MaterialTheme {
        Surface {
            AddEventDialog(
                onDismiss = {},
                onConfirm = { _, _, _, _, _ -> }
            )
        }
    }
}

@Preview(
    name = "Edit Event Dialog",
    showBackground = true
)
@Composable
fun EditEventDialogPreview() {
    val sampleEvent = getSampleEvents().first()

    MaterialTheme {
        Surface {
            EditEventDialog(
                event = sampleEvent,
                onDismiss = {},
                onConfirm = {}
            )
        }
    }
}