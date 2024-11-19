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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.queue_it.common.BottomNav
import com.example.queue_it.local.LocalStorage
import com.example.queue_it.navigation.Screen
import com.example.queue_it.theme.QueueItTheme
import com.example.queue_it.ui.add_event.AddEventScreen
import com.example.queue_it.ui.business_register.RegisterBusinessScreen
import com.example.queue_it.ui.businessqueue.BusinessEventsScreen
import com.example.queue_it.ui.customer_register.RegisterCustomerScreen
import com.example.queue_it.ui.event_details.EventDetailsScreen
import com.example.queue_it.ui.home.HomeScreen
import com.example.queue_it.ui.home.HomeViewModel
import com.example.queue_it.ui.login.LoginScreen
import com.example.queue_it.ui.notifications.NotificationScreen
import com.example.queue_it.ui.notifications.NotificationViewModel
import com.example.queue_it.ui.profile.ProfileScreen
import com.example.queue_it.ui.queue.QueueScreen
import com.example.queue_it.ui.queue.QueueScreenViewModel
import com.example.queue_it.ui.queue_details.QueueDetailsScreen
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
    val user =
        LocalStorage.getUserToken(LocalContext.current.applicationContext).collectAsState("none")

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
            startDestination = if (user.value == "none") Screen.Onboarding.route else Screen.Home.route,
            modifier = Modifier
                .padding(innerPadding)
                .background(color = Color.Black)
        ) {
            composable(Screen.Onboarding.route) { OnboardingScreen(navController) }

            composable(Screen.Signup.route) { SignUpScreen(navController) }

            composable(Screen.Login.route) {
                LoginScreen(
                    navController,
                )
            }

            composable(Screen.Home.route) { HomeScreen(viewModel = HomeViewModel(), navController) }

            composable(Screen.Queues.route) {
                QueueScreen(
                    viewModel = QueueScreenViewModel(),
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    navigateToLogin = { navController.navigate(Screen.Login.route) })
            }

            composable(Screen.Notification.route) { NotificationScreen(viewModel = NotificationViewModel()) }

            composable(Screen.RegisterBusiness.route) {
                RegisterBusinessScreen(onSuccess = {
                    navController.navigate(Screen.BusinessEventScreen.route)
                }
                )
            }

            composable(Screen.CreateEventScreen.route) {
                AddEventScreen(navigateBack = { navController.popBackStack() })
            }

            composable(Screen.RegisterCustomer.route) {
                RegisterCustomerScreen({ navController.navigate(Screen.Home.route) })
            }

            composable(
                route = "event-details/{eventId}",
                arguments = listOf(navArgument("eventId") { type = NavType.IntType })
            ) { backStackEntry ->
                // Extract the integer argument
                val eventId = backStackEntry.arguments?.getInt("eventId")
                if (eventId != null) {
                    EventDetailsScreen(
                        eventId,
                        { navController.popBackStack() },
                        { navController.navigate(Screen.getQueueDetailsScreen(it).route) }
                    )
                }
            }

            composable(
                route = "queue-details/{queueId}",
                arguments = listOf(navArgument("queueId") {type = NavType.IntType} )
            ) {
                val queueId = it.arguments?.getInt("queueId")
                if (queueId != null) {
                    QueueDetailsScreen(queueId)
                }
            }

            composable(Screen.BusinessEventScreen.route) {
                BusinessEventsScreen(
                    navigateToCreateEventScreen = {
                        navController.navigate(Screen.CreateEventScreen.route)
                    },
                    onBack = { navController.popBackStack() },
                    navigateToEventDetailsScreen = {
                        navController.navigate(
                            Screen.getEventDetailsScreen(
                                it
                            ).route
                        )
                    })
            }
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