package com.example.queue_it.ui.customer_register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.queue_it.R
import com.example.queue_it.common.GradientButton
import com.example.queue_it.model.Customer
import com.example.queue_it.model.Gender

@Composable
fun RegisterCustomerScreen(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterCustomerViewModel = viewModel()
) {

    val uiState = viewModel.uiState.collectAsState()
    val context = LocalContext.current.applicationContext
    val expanded = remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, bottom = 16.dp, start = 32.dp, end = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentWidth()
                    .align(alignment = Alignment.CenterHorizontally)
                    .width(100.dp)
                    .height(100.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.undraw_login__1_),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "Enter Your Details",
                color = Color(0xFF64B5F6),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email Field
            OutlinedTextField(
                value = uiState.value.name,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Name", color = Color(0xFF64B5F6)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(0xFF64B5F6),
                    focusedTextColor = Color.White
                ),
                singleLine = true,
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Password Field
            OutlinedTextField(
                value = uiState.value.age,
                onValueChange = { viewModel.onAgeChange(it) },
                label = { Text("Age", color = Color(0xFF64B5F6)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(0xFF64B5F6),
                    focusedTextColor = Color.White
                ),
                singleLine = true,
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.value.selected.name,
                onValueChange = {},
                label = { Text("Gender", color = Color(0xFF64B5F6)) },
                trailingIcon = {
                    IconButton(onClick = { expanded.value = true }) {
                        Icon(Icons.Default.ArrowDropDown, "dropdown menu")
                    }
                },
                readOnly = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(0xFF64B5F6),
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            ) {

                DropdownMenuItem(
                    text = { Text("Male") },
                    onClick = {
                        expanded.value = false
                        viewModel.onGenderChange(Gender.MALE)
                    }
                )

                DropdownMenuItem(
                    text = { Text("Female") },
                    onClick = {
                        expanded.value = false
                        viewModel.onGenderChange(Gender.FEMALE)
                    }
                )

                DropdownMenuItem(
                    text = { Text("Other") },
                    onClick = {
                        expanded.value = false
                        viewModel.onGenderChange(Gender.OTHER)
                    }
                )

                DropdownMenuItem(
                    text = { Text("Rather Not Say") },
                    onClick = {
                        expanded.value = false
                        viewModel.onGenderChange(Gender.NONE)
                    }
                )
            }

            Spacer(Modifier.height(16.dp))

            GradientButton(
                text = "Register",
                textSize = 18,
                cornerRadius = 16.dp,
                onClick = {
                    viewModel.registerCustomer(
                        context, Customer(
                            name = uiState.value.name,
                            age = uiState.value.age.toInt(),
                            gender = Gender.MALE
                        )
                    )

                    navigateToHome()
                },
                modifier = Modifier.fillMaxWidth()
            )

            uiState.value.errorMsg?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}