package com.example.queue_it.model

data class Customer (
    val customerId: Int,
    val customerName: String,
    val customerAge: Int,
    val customerGender: Char,
    val currentJoinedQueueId: MutableList<Ticket?>
)