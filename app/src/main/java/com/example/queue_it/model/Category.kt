package com.example.queue_it.model

import androidx.annotation.DrawableRes
import com.example.queue_it.R

enum class Category(val title: String, @DrawableRes val imageRes: Int, val id: Int) {
    ENTERTAINMENT("Entertainment", R.drawable.concert_queue_image, 0),
    SPORTS("Sports", R.drawable.sports, 2),
    EDUCATION("Education", R.drawable.conference_queue_image, 3),
    MEDICAL("Medical", R.drawable.hospital, 5),
    ECOMMERCE("Ecommerce", R.drawable.conference_queue_image, 4),
    OTHER("Other", R.drawable.queue_image, 6)
}
