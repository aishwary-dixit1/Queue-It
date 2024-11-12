package com.example.queue_it

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.queue_it.commonUI.BottomNav
import com.example.queue_it.navigation.Screen
import com.example.queue_it.theme.QueueItTheme
import com.example.queue_it.ui.home.HomeScreen
import com.example.queue_it.ui.home.HomeViewModel
import com.example.queue_it.ui.login.LoginScreen
import com.example.queue_it.ui.login.LoginScreenViewModel
import com.example.queue_it.ui.notifications.NotificationScreen
import com.example.queue_it.ui.notifications.NotificationViewModel
import com.example.queue_it.ui.profile.ProfileScreen
import com.example.queue_it.ui.profile.ProfileScreenViewModel
import com.example.queue_it.ui.queue.QueueScreen
import com.example.queue_it.ui.queue.QueueScreenViewModel
import com.example.queue_it.ui.signup.SignUpScreen
import com.example.queue_it.ui.splashscreen.OnboardingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()


           QueueItTheme {
               MainScreen(navController)
            }

        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Onboarding.route && currentRoute != Screen.Signup.route && currentRoute != Screen.Login.route) {
                Box(
                    modifier = Modifier
                        .navigationBarsPadding()
                ) {
                    BottomNav(navController = navController)
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Onboarding.route, //Screen.Home.route,
            modifier = Modifier
                .padding(innerPadding)
                .background(color = Color.Black)
        ) {
            composable(Screen.Onboarding.route) { OnboardingScreen(navController) }
            composable(Screen.Signup.route) { SignUpScreen(navController) }
            composable(Screen.Login.route) {LoginScreen(navController, viewModel = LoginScreenViewModel())}
            composable(Screen.Home.route) { HomeScreen(viewModel = HomeViewModel()) }
            composable(Screen.Queues.route) { QueueScreen(viewModel = QueueScreenViewModel(), onNavigateToEntertainment = { _, _ -> }) }
            composable(Screen.Profile.route) { ProfileScreen(viewModel = ProfileScreenViewModel()) }
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