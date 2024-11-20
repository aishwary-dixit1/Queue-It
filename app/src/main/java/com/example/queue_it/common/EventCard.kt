package com.example.queue_it.common

import android.R.attr.text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.queue_it.model.Event
import com.example.queue_it.util.convertEpochToDateTime

@Composable
fun EventCard(
    event: Event,
    address: String = "",
    navigateToEventDetailsScreen: (Int) -> Unit = {},
) {

    val start = convertEpochToDateTime(event.startTime).substringBeforeLast(":")
    val end = convertEpochToDateTime(event.endTime).substringBeforeLast(":")
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
                        text = "📍 $address",
                        style = MaterialTheme.typography.titleMedium
                    )

                    DateTimeDisplayLine(
                        "START ",
                        start.substringBefore(" "),
                        start.substringAfter(" ")
                    )
                    DateTimeDisplayLine("END     ", end.substringBefore(" "), end.substringAfter(" "))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "AVERAGE WAIT TIME",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "${event.waitTime}",
                            style = MaterialTheme.typography.displayMedium,
                            fontStyle = FontStyle.Italic,
                            color = Color(0xFF64B5F6)
                        )

                        Text(
                            text = " min",
                            style = MaterialTheme.typography.headlineLarge,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DateTimeDisplayLine(label: String, date: String, time: String) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = "$label:   ",
            color = MaterialTheme.colorScheme.primary
        )

        Text(text = "$date,  ")

        Text(
            text = time,
            color = Color(0xFF64B5F6),
            fontSize = 18.sp
        )
    }
}