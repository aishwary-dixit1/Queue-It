package com.example.queue_it.ui.queue

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.queue_it.common.LoadingScreen
import com.example.queue_it.common.RequestStatus
import com.example.queue_it.model.Event
import java.time.LocalDateTime

@Composable
fun QueueScreen(
    viewModel: QueueScreenViewModel = viewModel(),
) {
    val requestStatus by viewModel.requestStatus.collectAsState()
    when (requestStatus) {
        RequestStatus.Idle -> {}
        is RequestStatus.Error -> QueueScreen(
            emptyList(),
            (requestStatus as RequestStatus.Error).message
        )

        RequestStatus.Loading -> LoadingScreen()
        is RequestStatus.Success -> QueueScreen((requestStatus as RequestStatus.Success<List<Event>>).data)
    }
}

@Composable
fun QueueScreen(
    activeQueues: List<Event>,
    errorMsg: String? = null,
    viewModel: QueueScreenViewModel = viewModel()
) {

    val entertainmentSections by viewModel.entertainmentSections.collectAsState()

    if (errorMsg != null) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Text(errorMsg)
        }
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(16.dp)
        ) {
            Text(
                text = "My Active Queues",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            LazyColumn {
                items(items = activeQueues) {
                    EventCard(it, viewModel::mapUnixToDateTime)
                    Spacer(Modifier.height(16.dp))
                }

                item {
                    Text(
                        text = "While You Wait",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }

                itemsIndexed(entertainmentSections) { _, section ->
                    EntertainmentSection(
                        section = section,
                        onItemClick = {}
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun EventCard(
    event: Event,
    changeTimeFormat: (Long) -> LocalDateTime
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = event.title, style = MaterialTheme.typography.headlineSmall)
            Text(
                text = "${ changeTimeFormat(event.startTime) } - ${ changeTimeFormat(event.startTime) }",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Current Number",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(4.dp)
                    )

                    Text(
                        text = "#${45}",
                        style = MaterialTheme.typography.displaySmall,
                        fontStyle = FontStyle.Italic,
                        color = Color.Cyan,
                        modifier = Modifier.padding(4.dp)
                    )

                    Text(
                        text = "Your Number",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(4.dp)
                    )

                    Text(
                        text = "#${100}",
                        style = MaterialTheme.typography.displaySmall,
                        fontStyle = FontStyle.Italic,
                        color = Color.Cyan,
                        modifier = Modifier.padding(4.dp)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Time Left",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(4.dp)
                    )

                    Text(
                        text = "45 min.",
                        style = MaterialTheme.typography.displaySmall,
                        fontStyle = FontStyle.Italic,
                        color = Color.Cyan,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun QueueStatusCard(queueDetails: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Event Title Here",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QueueInfoItem("Token", "queueStatus.tokenNumber")
                QueueInfoItem("Position", "#{queueStatus.position}")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QueueInfoItem("People Ahead", "queueStatus.peopleAhead.toString()")
                QueueInfoItem("Wait Time", "queueStatus.expectedWaitTime")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Expected Turn: queueStatus.expectedTurnTime}",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF64B5F6)
            )
        }
    }
}

@Composable
fun QueueInfoItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun EntertainmentSection(
    section: EntertainmentSection,
    onItemClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = section.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(section.items) { _, item ->
                    EntertainmentItem(item) {
                        onItemClick(item)
                    }
                }
            }
        }
    }
}

@Composable
fun EntertainmentItem(
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .height(80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}