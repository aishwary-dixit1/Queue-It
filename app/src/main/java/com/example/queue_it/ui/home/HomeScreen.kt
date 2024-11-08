package com.example.queue_it.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.queue_it.R
import com.example.queue_it.commonUI.BottomNav
import com.example.queue_it.commonUI.EventsListScroll
import com.example.queue_it.commonUI.GradientButton
import com.example.queue_it.commonUI.GradientFloatingActionButton
import com.example.queue_it.commonUI.HeaderNav
import com.example.queue_it.commonUI.ImageCards
import com.example.queue_it.model.Category
import com.example.queue_it.model.EventDetails
import com.example.queue_it.navigation.Screen


@Composable
fun HomeScreen(viewModel: HomeViewModel, modifier: Modifier,
               navController: NavController) {
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()

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
        // Header Section
        HeaderNav(
            navController = navController,
            title = "Queue-it",
            location = "Kanpur",
            onSearchClick = { /*Handle search notification click*/ },
            onQrScanClick = { /*handle */ }
        )


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 2.dp)
            ) {
                item {
                    ImageCards(
                        imageDrawable = R.drawable.queue_image,
                        title = "In the Queue",
                        subtitle = "Anywhere, Anytime",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }

                // Get Started Button
                item {
                    GradientButton(
                        text = "Get Started",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(start = 8.dp, end = 8.dp),
                        textSize = 20,
                        cornerRadius = 30.dp,
                        onClick = { /* Handle click */ }
                    )
                }

                //Intro
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Welcome to Queue-It",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Queue It is your ultimate solution for managing and booking places in queues for various services like doctors, events, and more. Our platform ensures a seamless experience for both businesses and individuals, reducing wait times and improving efficiency.",
                            fontSize = 16.sp,
                        )
                    }
                }
                item {
                    // Categories Grid
                    CategoriesGrid(categories = uiState.categories)
                }
            }

        }

        // Floating Action Button
//        GradientFloatingActionButton(
//            onClick = { /* Handle click */ },
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(end = 16.dp, bottom = 76.dp)
//        )
    }
}
@Composable
fun CategoriesGrid(categories: List<Category>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Split categories into chunks of 2
        categories.chunked(2).forEach { row ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Create a column for each chunk in the row
                row.forEach { category ->
                    CategoryCard(
                        category = category,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }

}
@Composable
fun CategoryCard(category: Category, modifier: Modifier) {
    Column(modifier = modifier
        .padding(4.dp),
        verticalArrangement = Arrangement.Center) {
        Card(
            modifier = Modifier
                .padding(4.dp)
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
                    .background(color = Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(viewModel = HomeViewModel(), modifier = Modifier, navController = navController)
}