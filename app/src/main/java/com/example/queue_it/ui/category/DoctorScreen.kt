package com.example.queue_it.ui.category
//
//import androidx.compose.foundation.*
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material.icons.filled.Notifications
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.drawBehind
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.navOptions
//import coil.compose.AsyncImage
//import com.example.queue_it.R
//import com.example.queue_it.commonUI.HeaderNav
//import com.example.queue_it.model.Category
//import com.example.queue_it.ui.home.CategoryCard
//
//@Composable
//fun Category1Screen(
//    viewModel: CategoryViewModel,
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
////        HeaderNav(
////            navController = ,
////            title = "Category 1",
////            location = "Kanpur",
////            onSearchClick = { /*TODO*/ },
////            onNotificationClick = { /*TODO*/ }) {
////
////        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Text(
//            text = "Let's find\nyour suitable doctor",
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
//            CategoryCard(
//                category = uiState.categories[0],
//                modifier = Modifier.weight(1f)
//            )
//            CategoryCard(
//                category = uiState.categories[1],
//                modifier = Modifier.weight(1f)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Top Doctors Section
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = "Top doctors",
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold
//            )
//            TextButton(onClick = { /* Handle see all */ }) {
//                Text("See all",
//                    color = Color(0xFF42A5F5))
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Doctor Card
//        DoctorCard(doctor = uiState.topDoctors[0])
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
//fun CategoryCard(
//    category: DoctorCategory,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(160.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = if (category.type == CategoryType.CARDIO)
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
//                tint = if (category.type == CategoryType.CARDIO)
//                    Color(0xFF7B61FF) else Color(0xFF4B9AFF),
//                modifier = Modifier.size(32.dp)
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = category.name,
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//            Text(
//                text = "${category.doctorCount} doctors",
//                fontSize = 14.sp,
//                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
//            )
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.End
//            ) {
//                category.doctorAvatars.take(3).forEach { avatar ->
//                    AsyncImage(
//                        model = avatar,
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
//                if (category.doctorAvatars.size > 3) {
//                    Surface(
//                        modifier = Modifier.size(24.dp),
//                        shape = MaterialTheme.shapes.medium,
//                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
//                    ) {
//                        Text(
//                            text = "+${category.doctorAvatars.size - 3}",
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
//fun DoctorCard(doctor: Doctor) {
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
//                model = doctor.image,
//                contentDescription = "Doctor's photo",
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
//                    text = doctor.name,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Text(
//                    text = doctor.hospital,
//                    fontSize = 14.sp,
//                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
//                )
//                Text(
//                    text = doctor.specialty,
//                    fontSize = 14.sp,
//                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
//                )
//                Text(
//                    text = doctor.availableTime,
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
//
//@Preview(showBackground = true)
//@Composable
//fun DoctorScreenPreview(){
//    Category1Screen(
//        viewModel = CategoryViewModel(),
//        modifier = Modifier,
//        onSearchClick = { /*Handle search notification click*/},
//        onNotificationClick = { /* Handle notification click */ },
//        onQrScanClick = { /* Handle QR scan click */ }
//    )
//}