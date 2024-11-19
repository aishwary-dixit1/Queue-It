package com.example.queue_it.model

import kotlinx.serialization.Serializable

@Serializable
data class Queue(
    val eventId: Int = -1,
    val id: Int = -1,
    val title: String,
    val maxLimit: Int,
    val current: Int = -1,
    val size: Int = -1,
    val leftAt: Long = -1
)