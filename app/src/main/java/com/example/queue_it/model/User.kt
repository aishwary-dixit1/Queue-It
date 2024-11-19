package com.example.queue_it.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int = -1,
    val email: String,
    val password: String,
    val phoneNo: Long
)