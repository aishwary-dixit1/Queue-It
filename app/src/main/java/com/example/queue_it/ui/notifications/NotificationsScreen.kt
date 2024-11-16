package com.example.queue_it.ui.notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.queue_it.commonUI.GradientButton

@Composable
fun NotificationScreen(viewModel: NotificationViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 0.dp)
    ) {
        GradientButton(text = "Profile",
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            textSize = 20,
            cornerRadius = 30.dp,
            onClick = { /* Handle click */ }
        )
    }
}