package com.example.queue_it.ui.category
//
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.AsyncImage
//import com.example.queue_it.commonUI.HeaderNav
//import com.example.queue_it.model.Event
//
//@Composable
//fun CategoryScreen(
//    viewModel: CategoriesScreenViewModel,
//    modifier: Modifier = Modifier,
//    onSearchClick: () -> Unit,
//    onNotificationClick: () -> Unit,
//    onQrScanClick: () -> Unit
//) {
//    val uiState by viewModel.uiState.collectAsState()
//
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        // Header
//        HeaderNav(
//            title = uiState.currentCategory?.title ?: "",
//            location = uiState.currentCategory?.location? : "",
//            onSearchClick = onSearchClick,
//            onNotificationClick = onNotificationClick,
//            onQrScanClick = onQrScanClick
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Text(
//            text = "Let's find\nyour suitable event",
//            fontSize = 28.sp,
//            fontWeight = FontWeight.Bold,
//            lineHeight = 36.sp
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Category Cards
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            uiState.categories.forEach { category ->
//                CategoriesCard(
//                    category = category,
//                    modifier = Modifier.weight(1f)
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Top Events Section
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = "Top events",
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold
//            )
//            TextButton(onClick = { /* Handle see all */ }) {
//                Text("See all",
//                    color = Color(0xFF42A5F5)
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Event Card
//        uiState.topEvents.forEach { event ->
//            EventCard(event = event)
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//
//        Spacer(modifier = Modifier.weight(1f))
//
//        // Bottom Navigation
//        BottomNavigation(
//            selectedItem = uiState.selectedNavItem,
//            onItemSelected = viewModel::onNavItemSelected
//        )
//    }
//}
//
//@Composable
//fun CategoriesCard(
//    category: BusinessCategory,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(160.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = if (category.type == CategoryType.CONCERT)
//                Color(0xFFF0EEFF) else Color(0xFFEFF6FF)
//        )
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxSize()
//        ) {
//            Icon(
//                imageVector = category.icon,
//                contentDescription = null,
//                tint = if (category.type == CategoryType.CONCERT)
//                    Color(0xFF7B61FF) else Color(0xFF4B9AFF),
//                modifier = Modifier.size(32.dp)
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = category.title,
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//            Text(
//                text = "${category.eventCount} events",
//                fontSize = 14.sp,
//                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
//            )
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.End
//            ) {
//                category.eventImages.take(3).forEach { image ->
//                    AsyncImage(
//                        model = image,
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(24.dp)
//                            .clip(MaterialTheme.shapes.medium)
//                            .border(
//                                1.dp,
//                                MaterialTheme.colorScheme.surface,
//                                MaterialTheme.shapes.medium
//                            )
//                    )
//                }
//                if (category.eventImages.size > 3) {
//                    Surface(
//                        modifier = Modifier.size(24.dp),
//                        shape = MaterialTheme.shapes.medium,
//                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
//                    ) {
//                        Text(
//                            text = "+${category.eventImages.size - 3}",
//                            modifier = Modifier.padding(4.dp),
//                            fontSize = 10.sp
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun EventCard(
//    event: Event
//) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = Color(0xFFE3F2FD),
//            contentColor = Color(0xFF0D47A1)
//        )
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            AsyncImage(
//                model = event.imageResId,
//                contentDescription = event.title,
//                modifier = Modifier
//                    .size(80.dp)
//                    .clip(MaterialTheme.shapes.medium),
//                contentScale = ContentScale.Crop
//            )
//
//            Spacer(modifier = Modifier.width(16.dp))
//
//            Column {
//                Text(
//                    text = event.title,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Text(
//                    text = event.venue,
//                    fontSize = 14.sp,
//                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
//                )
//                Text(
//                    text = event.eventCategory,
//                    fontSize = 14.sp,
//                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
//                )
//                Text(
//                    text = event.date.toString(),
//                    fontSize = 14.sp,
//                    color = MaterialTheme.colorScheme.primary
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun BottomNavigation(
//    selectedItem: NavItem,
//    onItemSelected: (NavItem) -> Unit
//) {
//    NavigationBar {
//        NavItem.values().forEach { item ->
//            NavigationBarItem(
//                selected = selectedItem == item,
//                onClick = { onItemSelected(item) },
//                icon = {
//                    Icon(
//                        imageVector = item.icon,
//                        contentDescription = item.title,
//                    )
//                },
//                label = { Text(item.title) }
//            )
//        }
//    }
//}