package com.example.queue_it.model

import android.hardware.biometrics.BiometricManager.Strings
import android.text.format.DateFormat
import java.sql.Timestamp

data class Event(
    val id : Int,
    val openingTime : String?,
    val time : String,
    val closingTime : String?,
    val businessId: Business?,
    val eventCategory: String,
    val columnsId: Columns?,
    val title: String,
    val description: String,
    val avgWaitingTime: String,
    val date: String,
    val venue: String,
    val imageResId: Int
)
