package com.example.queue_it.model

import kotlinx.serialization.Serializable

@Serializable
data class Queue(
    val id: Int = -1,
    val title: String,
    val maxLimit: Int,
)