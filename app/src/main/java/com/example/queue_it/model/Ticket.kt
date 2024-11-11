package com.example.queue_it.model

import java.sql.Timestamp

data class Ticket(
    val ticketId: Int,
    val entrantId: Int,
    val columnId: Columns?,
    val expectedWaitTime: Timestamp
)