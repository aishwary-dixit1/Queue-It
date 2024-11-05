package com.example.queue_it.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.queue_it.R
import com.example.queue_it.common.EventsScreen
import com.example.queue_it.common.GoogleFont
import com.example.queue_it.common.GradientButton
import com.example.queue_it.model.Category
import com.example.queue_it.model.QueueDetails
import com.example.queue_it.theme.fontFamily
import com.example.queue_it.theme.varelaRoundFontfamily


@Composable
fun HomeScreen(viewModel: HomeViewModel, modifier: Modifier) {

    val uiState by viewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp)
                //.verticalScroll(scrollState)
        ) {
            // Header Section
            Header(
                onNotificationClick = { /* Handle notification click */ },
                onQrScanClick = { /* Handle QR scan click */ }
            )

            // Categories Grid
            CategoriesGrid(categories = uiState.categories)

            //Get Started Button
            GradientButton(text = "Get Started",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                textSize = 20,
                cornerRadius = 30.dp,
                onClick = { /* Handle click */ }
            )

            EventsScreen(
                eventCategory = "Laughter Therapy",
                eventsList = viewModel.laughterTherapyEvents
            )
            EventsScreen(
                eventCategory = "Music Concerts",
                eventsList = viewModel.musicConcertEvents
            )
        }

        // Floating Action Button
        GradientFloatingActionButton(
            onClick = { /* Handle click */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 76.dp)
        )

        // Bottom Navigation
        BottomNav(
            viewModel = viewModel,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@Composable
fun GradientFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(56.dp)
            .shadow(elevation = 6.dp, shape = CircleShape)
            .clip(CircleShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF64B5F6), // Light Blue
                        Color(0xFF1976D2)  // Blue
                    )
                )
            )
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            tint = Color.White,
            modifier = Modifier.size(36.dp)
        )
    }
}


@Composable
fun Header(
    onNotificationClick: () -> Unit,
    onQrScanClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Column {
                Text(
                    text = "Queue-It",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = varelaRoundFontfamily,
                    color = Color(0xFF42A5F5)
                )
                Text(
                    text = "> Kanpur",
                    fontSize = 14.sp
                )
            }
            Row {
                IconButton(
                    onClick = onNotificationClick,
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
                    onClick = onQrScanClick,
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


@Composable
fun CategoriesGrid(categories: List<Category>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(categories.size) { index ->
            CategoryCard(category = categories[index])
        }
    }
}
@Composable
fun CategoryCard(category: Category) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(1.5f),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE3F2FD),
            contentColor = Color(0xFF0D47A1)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFCCE5FC)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = category.imageRes),
                contentDescription = category.name,
                modifier = Modifier
                    .size(48.dp)
                    .padding(bottom = 8.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = category.name,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}


@Composable
fun GetStartedButton() {
    Button(
        onClick = { /* Handle click */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text("Get Started")
    }
}

@Composable
fun BottomNav(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val selectedItemIndex by viewModel.selectedItemIndex.collectAsState()

    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .wrapContentSize(align = Alignment.BottomCenter)
            .clip(RoundedCornerShape(16.dp)),
        containerColor = Color(0xFFCCE5FC)
    ) {
        // First item
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(36.dp)
                )
            },
            //label = { Text("Home") },
            selected = selectedItemIndex == 0,
            onClick = { viewModel.onItemSelected(0) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color(0xFF42A5F5),
                indicatorColor = Color(0xFF42A5F5)
            )
        )

        // Second item
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Black,
                    modifier = Modifier.size(36.dp)
                )
            },
            //label = { Text("Search") },
            selected = selectedItemIndex == 1,
            onClick = { viewModel.onItemSelected(1) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color(0xFF42A5F5),
                indicatorColor = Color(0xFF42A5F5)
            )
        )

        // Third item
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.Black,
                    modifier = Modifier.size(36.dp)
                )
            },
            //label = { Text("Profile") },
            selected = selectedItemIndex == 2,
            onClick = { viewModel.onItemSelected(2) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color(0xFF42A5F5),
                indicatorColor = Color(0xFF42A5F5)
            )
        )
    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(viewModel = HomeViewModel(), modifier = Modifier)
}