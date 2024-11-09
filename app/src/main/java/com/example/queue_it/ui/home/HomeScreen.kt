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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.queue_it.R
import com.example.queue_it.commonUI.GradientButton
import com.example.queue_it.commonUI.HeaderNav
import com.example.queue_it.commonUI.ImageCards
import com.example.queue_it.model.Category

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    // Observing UI state from the ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize() // Makes the Box occupy the entire screen size
            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 2.dp) // Small padding at the bottom for spacing
        ) {
            // Header section with navigation and actions
            HeaderNav(
                navController = navController,
                title = "Queue-it",
                location = "Kanpur",
                onSearchClick = { /* Handle search click */ },
                onQrScanClick = { /* Handle QR scanning click */ }
            )

            // Scrollable list for displaying content in a vertical arrangement
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 2.dp)
            ) {
                // Displaying an image card as the first item in the list
                item {
                    ImageCards(
                        imageDrawable = R.drawable.queue_image,
                        title = "In the Queue",
                        subtitle = "Anywhere, Anytime",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp) // Image height
                            .padding(8.dp) // Spacing around the image card
                            .clip(RoundedCornerShape(16.dp)) // Rounded corners for the image
                    )
                }

                // 'Get Started' button item with gradient styling
                item {
                    GradientButton(
                        text = "Get Started",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(start = 8.dp, end = 8.dp), // Padding for left and right
                        textSize = 20, // Font size for the button text
                        cornerRadius = 30.dp, // Corner radius for rounded button edges
                        onClick = { /* Handle click */ }
                    )
                }

                // Intro section for displaying welcome text and app description
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp) // Padding around intro text
                    ) {
                        Text(
                            text = "Welcome to Queue-It",
                            fontSize = 24.sp, // Large font size for emphasis
                            fontWeight = FontWeight.Bold // Bold font for the welcome title
                        )
                        Spacer(modifier = Modifier.height(4.dp)) // Space between title and description

                        Text(
                            text = "Queue It is your ultimate solution for managing and booking places in queues for various services like doctors, events, and more. Our platform ensures a seamless experience for both businesses and individuals, reducing wait times and improving efficiency.",
                            fontSize = 16.sp // Regular font size for descriptive text
                        )
                    }
                }

                // Categories grid to showcase different queue categories
                item {
                    CategoriesGrid(categories = uiState.categories)
                }
            }
        }

        // Floating Action Button (FAB) for additional actions, such as showing current status
        /* Uncomment to enable the FAB
        GradientFloatingActionButton(
            onClick = { /* Handle click */ },
            modifier = Modifier
                .align(Alignment.BottomEnd) // Positioning FAB at the bottom right
                .padding(end = 16.dp, bottom = 76.dp) // Padding from screen edges
        ) */
    }
}

// Displays a grid layout for queue categories, with each category in a card
@Composable
fun CategoriesGrid(categories: List<Category>) {
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
                        modifier = Modifier.weight(1f) // Equal weight for balanced layout
                    )
                }
            }
        }
    }
}

// Displays individual category information inside a styled card
@Composable
fun CategoryCard(category: Category, modifier: Modifier) {
    Column(
        modifier = modifier.padding(4.dp), // Small padding around each card
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .padding(4.dp) // Padding within each card
                .aspectRatio(1.5f), // Aspect ratio for card shape
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE3F2FD), // Background color of the card
                contentColor = Color(0xFF0D47A1) // Text color for the card content
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Elevation for shadow effect
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White), // Background color inside the card
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Image and text for the category card
                ImageCards(
                    imageDrawable = category.imageRes,
                    title = "",
                    subtitle = category.name,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// A reusable button composable for 'Get Started' action with customizable styling
@Composable
fun GetStartedButton() {
    Button(
        onClick = { /* Handle click */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp), // Vertical padding for spacing
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary // Button color
        )
    ) {
        Text("Get Started") // Button text
    }
}

// Preview function for HomeScreen composable
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController() // Initializing a NavController for preview
    HomeScreen(viewModel = HomeViewModel())
}
