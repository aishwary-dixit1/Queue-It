package com.example.queue_it.util

import android.util.Log
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun convertToMillis(dateTimeString: String): Long {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
    return try {
        val localDateTime = LocalDateTime.parse(dateTimeString, formatter)
        // Use ZoneId for IST (Indian Standard Time)
        val x = localDateTime.atZone(ZoneId.of("Asia/Kolkata")).toInstant().toEpochMilli()
        Log.d("check", x.toString())
        x
    } catch (e: Exception) {
        println("Error parsing date: ${e.message}")
        -1
    }
}

fun convertEpochToDateTime(epochMillis: Long): String {
    // Define the formatter for the desired format
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        .withZone(ZoneId.of("Asia/Kolkata")) // Set to IST (Indian Standard Time)

    // Convert epoch milliseconds to a formatted date-time string
    return formatter.format(Instant.ofEpochMilli(epochMillis))
}

