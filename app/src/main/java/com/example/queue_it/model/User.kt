package com.example.queue_it.model

data class User (
    val id : Int,
    val email : String,
    val password : String,
    val phoneNumber : Long,
    val business_id : Business?,
    val customer_id : Customer?,
)