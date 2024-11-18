package com.example.queue_it.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.queue_it.R
import com.example.queue_it.model.Event

@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    onEventClick: (Event) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Surface(
        modifier = modifier.fillMaxSize(),
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
                name = uiState.name,
                modifier = Modifier.size(120.dp)
            )

            Text(
                text = uiState.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )

            ProfileField(
                label = "Email",
                value = uiState.email,
                modifier = Modifier.fillMaxWidth()
            )

            ProfileField(
                label = "Phone Number",
                value = uiState.phoneNumber,
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
                    value = uiState.age.toString(),
                    modifier = Modifier.weight(1f)
                )

                ProfileField(
                    label = "Gender",
                    value = uiState.gender,
                    modifier = Modifier.weight(1f)
                )
            }

            // Logout Button
            TextButton(
                onClick = { viewModel.signOut(context) { navigateToLogin() } },
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
fun EventItem(
    event: Event,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                contentDescription = "Calendar",
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )

            // Event Details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = event.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "",  // TODO
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_24),
                contentDescription = "View Details",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
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

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val previewViewModel = remember {
        ProfileScreenViewModel(initialUser = SampleData.sampleUser)
    }

    ProfileScreen(
        viewModel = previewViewModel,
        {}
    )
}