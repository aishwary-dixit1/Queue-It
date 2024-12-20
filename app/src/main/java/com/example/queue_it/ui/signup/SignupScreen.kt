package com.example.queue_it.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.queue_it.R
import com.example.queue_it.commonUI.GradientButton
import com.example.queue_it.navigation.Screen

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpScreenViewModel = SignUpScreenViewModel(),
    onSignUpSuccess: (String, String, String) -> Unit = { _, _, _ -> }
) {
    // Use collectAsState() to observe the StateFlow
    val uiState by viewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, bottom = 16.dp, start = 32.dp, end = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            // Logo Image
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
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "Sign Up",
                color = Color(0xFF64B5F6),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Name Field
            OutlinedTextField(
                value = uiState.name,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Name", color = Color(0xFF64B5F6)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(0xFF64B5F6),
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email Field
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Email", color = Color(0xFF64B5F6)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(0xFF64B5F6),
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Phone Number Field
            OutlinedTextField(
                value = uiState.phoneNumber,
                onValueChange = { viewModel.onPhoneNumberChange(it) },
                label = { Text("Phone Number", color = Color(0xFF64B5F6)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(0xFF64B5F6),
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Password Field
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = { Text("Password", color = Color(0xFF64B5F6)) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(0xFF64B5F6),
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Confirm Password Field
            OutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordChange(it) },
                label = { Text("Confirm Password", color = Color(0xFF64B5F6)) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(0xFF64B5F6),
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sign Up Button
            GradientButton(
                text = "Sign Up",
                textSize = 18,
                cornerRadius = 16.dp,
                onClick = { navController.navigate(Screen.Login.route)
//                    viewModel.signUp()
//                    if (uiState.isSignUpSuccessful) {
//                        onSignUpSuccess(uiState.name, uiState.email, uiState.phoneNumber)
//                        navController.navigate(Screen.Login.route)
//                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Login Link
            TextButton(
                onClick = { navController.navigate(Screen.Login.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Already have an account? Login", color = Color(0xFF42A5F5))
            }

            // Error Message
            uiState.errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    val viewModel = SignUpScreenViewModel()

    SignUpScreen(
        navController = rememberNavController(),
        viewModel = viewModel,
        onSignUpSuccess = { username, email, phoneNumber ->
            // Handle sign-up success logic here if needed
            println("Signed up with Username: $username, Email: $email, Phone: $phoneNumber")
        }
    )
}
