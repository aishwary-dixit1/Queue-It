package com.example.queue_it.model

data class Business(
    val id: Int,
    val businessName: String,
    val address: String,
    val businessCategory: String,
    val currentOpenEventsId: MutableList<Event?>,
    val historyOfQueuesId: MutableList<Event?>
    )