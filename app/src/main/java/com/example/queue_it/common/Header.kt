package com.example.queue_it.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.queue_it.R
import com.example.queue_it.navigation.Screen
import com.example.queue_it.theme.varelaRoundFontfamily

@Composable
fun HeaderNav(
    navController: NavHostController,
    title: String,
    location: String,
    onSearchClick: (String) -> Unit,
    onQrScanClick: () -> Unit

) {

    val searchSelected = remember { mutableStateOf(false) }
    val searchKey = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .drawBehind {
                val strokeWidth = 3.dp.toPx()
                val positionY = size.height - strokeWidth
                val gradient = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF64B5F6), // Light Blue
                        Color(0xFF1976D2)  // Blue
                    )
                )
                drawRect(
                    brush = gradient,
                    size = Size(size.width, strokeWidth),
                    topLeft = Offset(0f, positionY)
                )
            }
    ) {
        if (searchSelected.value) {
            OutlinedTextField(
                value = searchKey.value,
                onValueChange = { searchKey.value = it },
                placeholder = { Text("Search") },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            searchSelected.value = false
                            onSearchClick(searchKey.value)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search"
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true,
                shape = CircleShape,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.DarkGray,
                    focusedContainerColor = Color.DarkGray,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                )
            )
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Column {
                    Text(
                        text = title,
                        fontSize = 28.sp,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = varelaRoundFontfamily,
                        color = Color(0xFF42A5F5)
                    )
                }
                Row {
                    IconButton(
                        onClick = { searchSelected.value = true },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(
                        onClick = { navController.navigate(Screen.Notification.route) },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            modifier = Modifier.size(30.dp)

                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(
                        onClick = { onQrScanClick },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            painterResource(id = R.drawable.baseline_qr_code_scanner_24),
                            contentDescription = "QR Code Scanner",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderNavPreview() {
    val navController = rememberNavController()
    HeaderNav(
        navController = navController,
        title = "Queue-it",
        location = "Kanpur",
        onSearchClick = { /*Handle search notification click*/ },
        onQrScanClick = { /* Handle QR scan click */ }
    )
}
