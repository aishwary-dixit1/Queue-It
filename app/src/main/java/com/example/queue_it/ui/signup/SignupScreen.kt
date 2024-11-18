package com.example.queue_it.ui.signup

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.queue_it.R
import com.example.queue_it.common.GradientButton
import com.example.queue_it.navigation.Screen

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpScreenViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current.applicationContext
    val requestStatus = viewModel.requestStatus.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 16.dp, start = 32.dp, end = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(50.dp))

        Box(modifier = Modifier
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

        Text("Create an Account", color = Color(0xFF64B5F6), style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

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

        GradientButton(
            text = "Sign Up",
            textSize = 18,
            cornerRadius = 16.dp,
            onClick = {
                viewModel.handleSignUp(context) { navController.navigate(Screen.Home.route) }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(
            onClick = { navController.navigate(Screen.Login.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Already have an account? Login", color = Color(0xFF42A5F5))
        }

        uiState.errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen(
        navController = rememberNavController(),
    )
}
