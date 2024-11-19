package com.example.queue_it.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.queue_it.common.LoadingScreen
import com.example.queue_it.common.RequestStatus

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    navigateToLogin: () -> Unit,
) {
    val context = LocalContext.current.applicationContext
    viewModel.loadUser(context)
    val requestStatus by viewModel.requestStatus.collectAsState()
    when (requestStatus) {
        RequestStatus.Loading -> LoadingScreen()
        is RequestStatus.Success -> ProfileScreenContent(viewModel, navigateToLogin)
        RequestStatus.Idle -> {}
        is RequestStatus.Error -> {}
    }
}

@Composable
fun ProfileScreenContent(
    viewModel: ProfileViewModel,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current.applicationContext

    Surface(
        modifier = modifier.fillMaxSize().statusBarsPadding(),
        color = MaterialTheme.colorScheme.surface
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Section
            Text(
                text = "Profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            InitialAvatar(
                name = uiState.customer.name,
                modifier = Modifier.size(120.dp)
            )

            Text(
                text = uiState.customer.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )

            ProfileField(
                label = "Email",
                value = uiState.user.email,
                modifier = Modifier.fillMaxWidth()
            )

            ProfileField(
                label = "Phone Number",
                value = uiState.user.phoneNo.toString(),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProfileField(
                    label = "Age",
                    value = uiState.customer.age.toString(),
                    modifier = Modifier.weight(1f)
                )

                ProfileField(
                    label = "Gender",
                    value = uiState.customer.gender.name,
                    modifier = Modifier.weight(1f)
                )
            }

            // Logout Button
            TextButton(
                onClick = {
                    viewModel.signOut(context)
                    navigateToLogin()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
            ) {
                Text(
                    text = "Logout",
                    fontSize = 16.sp,
                    color = Color(0xFF42A5F5)
                )
            }

            // Help & Support Button
            TextButton(
                onClick = { /* Handle help & support */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Help & Support",
                    fontSize = 16.sp,
                    color = Color(0xFF42A5F5)
                )
            }

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomCenter) {
                Text("© Sin-Tax, 2024")
            }
        }
    }
}

@Composable
fun InitialAvatar(
    name: String,
    modifier: Modifier = Modifier
) {
    val initial = name.firstOrNull()?.uppercase() ?: "?"

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color = Color(0xFF42A5F5)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ProfileField(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = value,
                modifier = Modifier.padding(16.dp),
                fontSize = 16.sp
            )
        }
    }
}