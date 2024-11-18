package com.example.queue_it.ui.business_register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.queue_it.R
import com.example.queue_it.common.GradientButton
import com.example.queue_it.common.LoadingScreen
import com.example.queue_it.common.RequestStatus
import com.example.queue_it.model.EventCategory

@Composable
fun RegisterBusinessScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterBusinessViewModel = viewModel(),
    onSuccess: () -> Unit,
) {

    var name = remember { mutableStateOf("") }
    var address = remember { mutableStateOf("") }
    var expanded = remember { mutableStateOf(false) }
    var selected = remember { mutableStateOf(EventCategory.ENTERTAINMENT) }
    val requestStatus = viewModel.requestStatus.collectAsState().value
    val context = LocalContext.current.applicationContext

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
                "Register Business",
                color = Color(0xFF64B5F6),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("Business Name", color = Color(0xFF64B5F6)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(0xFF64B5F6),
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)

            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = address.value,
                onValueChange = { address.value = it },
                label = { Text("Address", color = Color(0xFF64B5F6)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1976D2),
                    unfocusedBorderColor = Color(0xFF64B5F6),
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
//                OutlinedTextField(
//                    value = selected.value,
//                    onValueChange = { selected.value = it },
//                    //label = { Text("Category") },
//                    //trailingIcon = {
//                        IconButton(onClick = { expanded.value = true }) {
//                            Icon(Icons.Filled.ArrowDropDown, "contentDescription")
//                        },
//                    //},
//                    //readOnly = true,
//                    modifier = Modifier.fillMaxWidth()
//                )

                OutlinedTextField(
                    value = selected.value.name,
                    onValueChange = {},
                    label = { Text("Category", color = Color(0xFF64B5F6)) },
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
                        text = { Text("Entertainment") },
                        onClick = {
                            expanded.value = false
                            selected.value = EventCategory.ENTERTAINMENT
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Education") },
                        onClick = {
                            expanded.value = false
                            selected.value = EventCategory.EDUCATION
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Medical") },
                        onClick = {
                            expanded.value = false
                            selected.value = EventCategory.MEDICAL
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("E-Commerce") },
                        onClick = {
                            expanded.value = false
                            selected.value = EventCategory.ECOMMERCE
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Sports") },
                        onClick = {
                            expanded.value = false
                            selected.value = EventCategory.SPORTS
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Others") },
                        onClick = {
                            expanded.value = false
                            selected.value = EventCategory.OTHER
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            GradientButton(
                text = "Register Business",
                textSize = 18,
                cornerRadius = 16.dp,
                onClick = {
                    viewModel.registerBusiness(context, name.value, address.value, selected.value)
                    onSuccess()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        when (requestStatus) {
            is RequestStatus.Loading -> {
                LoadingScreen()
            }

            is RequestStatus.Error -> {}

            RequestStatus.Idle -> {}
            is RequestStatus.Success -> {
                onSuccess()
            }
        }
    }


}