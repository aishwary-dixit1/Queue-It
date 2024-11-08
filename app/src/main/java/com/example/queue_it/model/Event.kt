package com.example.queue_it.model

import android.text.format.DateFormat
import java.sql.Timestamp

data class Event(
    val id : Int,
    val openingTime : Timestamp,
    val closingTime : Timestamp,
    val businessId: Business?,
    val eventCategory: String,
    val columnsId: Columns?,
    val title: String,
    val description: String,
    val avgWaitingTime: Timestamp,
    val date: DateFormat,
    val venue: String,
    val imageResId: Int
)
