package com.example.queue_it.model

import androidx.annotation.DrawableRes
import com.example.queue_it.R

enum class Category(val title: String, @DrawableRes val imageRes: Int) {
    ENTERTAINMENT("Entertainment", R.drawable.concert_queue_image),
    SPORTS("Sports", R.drawable.sports),
    EDUCATION("Education", R.drawable.conference_queue_image),
    MEDICAL("Medical", R.drawable.hospital),
    ECOMMERCE("Ecommerce", R.drawable.conference_queue_image),
    OTHER("Other", R.drawable.queue_image)
}