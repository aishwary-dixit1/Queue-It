package com.example.queue_it.common

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar

@Composable
fun DateTimePicker(onDateTimeSelected: (LocalDate, LocalTime) -> Unit) {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    // Trigger DatePicker
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            onDateSelected = { date ->
                selectedDate = date
                showDatePicker = false
                showTimePicker = true // Prompt for time after date
            }
        )
    }

    // Trigger TimePicker
    if (showTimePicker) {
        TimePickerDialog(
            onDismissRequest = { showTimePicker = false },
            onTimeSelected = { time ->
                selectedTime = time
                showTimePicker = false
                if (selectedDate != null) {
                    onDateTimeSelected(selectedDate!!, selectedTime!!)
                }
            }
        )
    }

    Button(onClick = { showDatePicker = true }) {
        Text("Pick Start Date & Time")
    }

    // Display selected values
    Text(
        text = if (selectedDate != null && selectedTime != null) {
            "$selectedDate, $selectedTime"
        } else ""
    )
}

@Composable
fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    DisposableEffect(Unit) {
        datePickerDialog.show()
        onDispose { datePickerDialog.dismiss() }
    }
}

@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onTimeSelected: (LocalTime) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val timePickerDialog = android.app.TimePickerDialog(
        context,
        { _, hour, minute ->
            onTimeSelected(LocalTime.of(hour, minute))
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true // Use 24-hour format
    )

    DisposableEffect(Unit) {
        timePickerDialog.show()
        onDispose { timePickerDialog.dismiss() }
    }
}
