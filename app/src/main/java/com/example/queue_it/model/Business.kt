package com.example.queue_it.model

import kotlinx.serialization.Serializable

@Serializable
data class Business(
    val id: Int = -1,
    val name: String,
    val address: String,
    val category: EventCategory
)
