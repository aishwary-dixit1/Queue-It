package com.example.queue_it

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.queue_it.commonUI.BottomNav
import com.example.queue_it.navigation.Screen
import com.example.queue_it.theme.QueueItTheme
import com.example.queue_it.ui.home.HomeScreen
import com.example.queue_it.ui.home.HomeViewModel
import com.example.queue_it.ui.notifications.NotificationScreen
import com.example.queue_it.ui.notifications.NotificationViewModel
import com.example.queue_it.ui.profile.ProfileScreen
import com.example.queue_it.ui.profile.ProfileViewModel
import com.example.queue_it.ui.queue.QueueScreen
import com.example.queue_it.ui.queue.QueueViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()


           QueueItTheme {
               MainScreen(navController)
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    HomeScreen(viewModel = HomeViewModel(),
//                        modifier = Modifier.padding(innerPadding))
//                }
            }

        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNav(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
                .background(color = Color.Black)
        ) {
            composable(Screen.Home.route) { HomeScreen(viewModel = HomeViewModel(), modifier = Modifier, navController = navController) }
            composable(Screen.Queues.route) { QueueScreen(viewModel = QueueViewModel()) }
            composable(Screen.Profile.route) { ProfileScreen(viewModel = ProfileViewModel()) }
            composable(Screen.Notification.route) { NotificationScreen(viewModel = NotificationViewModel())}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QueueItTheme {
        val navController = rememberNavController()
        MainScreen(navController)

    }
}