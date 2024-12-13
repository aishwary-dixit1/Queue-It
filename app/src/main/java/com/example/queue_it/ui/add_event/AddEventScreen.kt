package com.example.queue_it.ui.add_event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.queue_it.model.Event
import com.example.queue_it.util.convertToMillis

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddEventViewModel = viewModel()
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var venue by remember { mutableStateOf("") }
    var waitTime by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    val context = LocalContext.current.applicationContext

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create New Event") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                isError = showError && title.isEmpty(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(0xFF4A8CB8),
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                isError = showError && description.isEmpty(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(0xFF4A8CB8),
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth(),
                isError = showError && category.isEmpty(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(0xFF4A8CB8),
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )

            OutlinedTextField(
                value = waitTime,
                onValueChange = { waitTime = it },
                label = { Text("Avg. Wait Time") },
                placeholder = { Text("In Minutes") },
                modifier = Modifier.fillMaxWidth(),
                isError = showError && waitTime.isEmpty(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(0xFF4A8CB8),
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )

            OutlinedTextField(
                value = startDate,
                onValueChange = { startDate = it },
                label = { Text("Start Date") },
                placeholder = { Text("DD-MM-YYYY") },
                modifier = Modifier.fillMaxWidth(),
                isError = showError && waitTime.isEmpty(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(0xFF4A8CB8),
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )

            OutlinedTextField(
                value = startTime,
                onValueChange = { startTime = it },
                label = { Text("Start Time") },
                placeholder = { Text("HH:MM:SS") },
                modifier = Modifier.fillMaxWidth(),
                isError = showError && waitTime.isEmpty(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(0xFF4A8CB8),
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )

            OutlinedTextField(
                value = endDate,
                onValueChange = { endDate = it },
                label = { Text("End Date") },
                placeholder = { Text("DD-MM-YYYY") },
                modifier = Modifier.fillMaxWidth(),
                isError = showError && waitTime.isEmpty(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(0xFF4A8CB8),
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )

            OutlinedTextField(
                value = endTime,
                onValueChange = { endTime = it },
                label = { Text("End Time") },
                placeholder = { Text("HH:MM:SS") },
                modifier = Modifier.fillMaxWidth(),
                isError = showError && waitTime.isEmpty(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(0xFF4A8CB8),
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )

            if (showError) {
                Text("Please fill in all fields")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                OutlinedButton(modifier = Modifier.weight(1f), onClick = navigateBack) {
                    Text("Cancel")
                }

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    onClick = {
                        if (title.isEmpty() || description.isEmpty() ||
                            category.isEmpty() || venue.isEmpty() || startTime.isEmpty()
                            || endTime.isEmpty() || startDate.isEmpty() || endDate.isEmpty()
                        ) {
                            showError = true
                        } else {
                            viewModel.addEvent(
                                context = context,
                                event = Event(
                                    title = title,
                                    description = description,
                                    startTime = convertToMillis("$startDate $startTime"),
                                    endTime = convertToMillis("$endDate $endTime"),
                                    category = enumValueOf(category),
                                    waitTime = waitTime.toInt(),
                                )
                            )

                            navigateBack()
                        }
                    }
                ) {
                    Text("Create")
                }
            }
        }
    }
}
