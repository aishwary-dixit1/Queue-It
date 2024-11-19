package com.example.queue_it.ui.event_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.queue_it.model.Category
import com.example.queue_it.model.Queue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(
    eventId: Int,
    navigateBack: () -> Unit,
    navigateToQueueDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel<EventDetailsViewModel>(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EventDetailsViewModel(eventId) as T
        }
    })
    val uiState = viewModel.uiState.collectAsState()
    val event = uiState.value.event
    val queues = uiState.value.eventQueues
    val category = Category.valueOf(event.category.toString())

    var showAddQueueDialog by remember {
        mutableStateOf(false)
    }

    Box(contentAlignment = Alignment.Center) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Event Details") }, navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Navigate Back"
                        )
                    }
                })
            },

            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
                        showAddQueueDialog = !showAddQueueDialog
                    },
                    icon = { Icon(Icons.Default.Add, "Add New Queue") },
                    text = { Text(text = "Add New Queue") },
                )
            },

            floatingActionButtonPosition = FabPosition.Center
        ) {
            Column(
                modifier = modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                Box(contentAlignment = Alignment.BottomStart) {
                    Image(
                        painter = painterResource(id = category.imageRes),
                        contentScale = ContentScale.Crop,
                        contentDescription = category.title,
                        modifier = Modifier
                            .fillMaxHeight(0.35f)
                            .fillMaxWidth()
                    )

                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black,
                                    ),
                                )
                            )
                    )

                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.displaySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = Color(0xFF64B5F6),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Active Queues",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = W700,
                    modifier = Modifier.padding(8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(queues) {
                        QueueCard(queue = it, navigateToQueueDetails = navigateToQueueDetails)
                    }
                }
            }
        }

        if (showAddQueueDialog) {
            AddQueueDialog(
                onDismiss = { showAddQueueDialog = false },
                onConfirm = { title, maxLimit ->
                    viewModel.addQueueToEvent(title, maxLimit, eventId)
                    showAddQueueDialog = false
                    viewModel.loadQueues()
                }
            )
        }
    }
}

@Composable
fun AddQueueDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, Int) -> Unit,
) {
    var title by remember {
        mutableStateOf("")
    }

    var maxLimit by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = { onConfirm(title, maxLimit.toInt()) }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },

        title = {
            Text(text = "Add New Queue")
        },

        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title", color = Color(0xFF64B5F6)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1976D2),
                        unfocusedBorderColor = Color(0xFF64B5F6),
                        focusedTextColor = Color.White
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )

                OutlinedTextField(
                    value = maxLimit,
                    onValueChange = { maxLimit = it },
                    label = { Text("Maximum Limit", color = Color(0xFF64B5F6)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF1976D2),
                        unfocusedBorderColor = Color(0xFF64B5F6),
                        focusedTextColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)

                )
            }
        }
    )
}

@Composable
fun QueueCard(
    queue: Queue,
    navigateToQueueDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.DarkGray, RoundedCornerShape(12.dp)),
        onClick = { navigateToQueueDetails(queue.id) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = queue.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = W600,
                modifier = Modifier.padding(8.dp),
                color = Color(0xFF64B5F6)
            )

            HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp), thickness = 2.dp)

            QueueInfoLine(label = "Current", value = queue.current)
            QueueInfoLine(label = "Total", value = queue.size)
            QueueInfoLine(label = "Max. Limit", value = queue.maxLimit)
        }
    }
}

@Composable
fun QueueInfoLine(label: String, value: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = W700,
        )

        Text(
            text = "#$value",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = W700,
            color = Color(0xFF64B5F6)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun QueueCardPreview() {
    QueueCard(
        modifier = Modifier.statusBarsPadding(),
        queue = Queue(title = "ABCD", maxLimit = 5),
        navigateToQueueDetails = {})
}