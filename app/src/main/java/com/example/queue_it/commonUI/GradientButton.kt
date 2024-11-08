package com.example.queue_it.commonUI

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import com.example.queue_it.theme.varelaRoundFontfamily

@Composable
fun GradientButton(
    text: String,
    modifier: Modifier = Modifier,
    textSize: Int = 16,
    cornerRadius: Dp = 8.dp,
    onClick: () -> Unit
) {
    androidx.compose.material3.Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(cornerRadius),
                clip = false
            )
            .drawBehind {
                val gradient = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF64B5F6), // Light Blue
                        Color(0xFF1976D2)  // Blue
                    )
                )
                drawRoundRect(
                    brush = gradient,
                    size = this.size,
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadius.toPx())
                )
            },
        shape = RoundedCornerShape(cornerRadius)
    ) {
        Text(
            text = text,
            fontSize = textSize.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = varelaRoundFontfamily,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

