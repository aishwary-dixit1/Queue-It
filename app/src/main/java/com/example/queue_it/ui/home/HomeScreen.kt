package com.example.queue_it.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.queue_it.R
import com.example.queue_it.common.GradientButton
import com.example.queue_it.common.HeaderNav
import com.example.queue_it.common.ImageCards
import com.example.queue_it.local.LocalStorage
import com.example.queue_it.model.Category
import com.example.queue_it.navigation.Screen

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    navController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current.applicationContext
    val businessToken = LocalStorage.getBusinessToken(context).collectAsState("")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 2.dp)
        ) {
            // Header section with navigation and actions
            HeaderNav(
                navController = navController,
                title = "Queue-It",
                location = "Kanpur",
                onSearchClick = { viewModel.searchText(it) },
                onQrScanClick = { /* Handle QR scanning click */ }
            )

            // Scrollable list for displaying content in a vertical arrangement
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp, bottom = 2.dp)
            ) {
                // Displaying an image card as the first item in the list
                item {
                    ImageCards(
                        imageDrawable = R.drawable.queue_image,
                        title = "In the Queue",
                        subtitle = "Anywhere, Anytime",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }

                // 'Get Started' button item with gradient styling
                item {
                    GradientButton(
                        text = "Your Queues",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(start = 8.dp, end = 8.dp),
                        textSize = 20,
                        cornerRadius = 30.dp,
                        onClick = {
                            navController.navigate(
                                if (businessToken.value == "none")
                                    Screen.RegisterBusiness.route
                                else Screen.BusinessEventScreen.route
                            )
                        }
                    )
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Welcome to Queue-It",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Queue It is your ultimate solution for managing and booking places in queues for various services like doctors, events, and more. Our platform ensures a seamless experience for both businesses and individuals, reducing wait times and improving efficiency.",
                            fontSize = 16.sp
                        )
                    }
                }

                item {
                    CategoriesGrid(navController, uiState.categories)
                }
            }
        }

//        GradientFloatingActionButton(
//            onClick = { /* Handle click */ },
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(end = 16.dp, bottom = 76.dp)
//        )
    }
}

@Composable
fun CategoriesGrid(
    navController: NavHostController,
    categories: List<Category>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Divides categories into rows of 2 for better layout
        categories.chunked(2).forEach { row ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Creates a card for each category in the row
                row.forEach { category ->
                    CategoryCard(
                        category = category,
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate(Screen.getEventListScreen(it).route) }
                    )
                }
            }
        }
    }
}

// Displays individual category information inside a styled card
@Composable
fun CategoryCard(category: Category, modifier: Modifier, onClick: (String) -> Unit) {
    Column(
        modifier = modifier.padding(4.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .padding(4.dp)
                .aspectRatio(1.5f), // Aspect ratio for card shape
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE3F2FD),
                contentColor = Color(0xFF0D47A1)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            onClick = { onClick(category.name) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ImageCards(
                    imageDrawable = category.imageRes,
                    title = "",
                    subtitle = category.title,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}