package com.example.queue_it.ui.queue_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.queue_it.model.Customer
import com.example.queue_it.model.Gender
import com.example.queue_it.util.convertEpochToDateTime

@Composable
fun QueueDetailsScreen(
    queueId: Int,
    modifier: Modifier = Modifier,
) {

    val viewModel = viewModel<QueueDetailsViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return QueueDetailsViewModel(queueId) as T
            }
        }
    )
    val uiState = viewModel.uiState.collectAsState().value
    val showDialog = remember {
        mutableStateOf(false)
    }
    var selectedCustomer = -1
    val currentCustomer = remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.removeCustomer(queueId, currentCustomer.intValue) },
                text = { Text("Move To Next", fontSize = 16.sp) },
                //shape = CircleShape,
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowForward,
                        contentDescription = "Move To Next",
                        modifier = Modifier.size(28.dp)
                    )
                })
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = uiState.queue.title,
                        style = MaterialTheme.typography.displayMedium
                    )

                    Text(
                        text = "Max. Limit: ${uiState.queue.maxLimit}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                IconButton(onClick = { viewModel.loadCustomers() }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh List",
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            HorizontalDivider()

            LazyColumn {
                itemsIndexed(uiState.customers) { index, customer ->

                    if (index == 0) currentCustomer.intValue = customer.id
                    CustomerCard(
                        index = index + 1,
                        current = uiState.queue.current,
                        waitTime = System.currentTimeMillis()
                            .plus(uiState.event.waitTime * (index) * 60 * 1000),
                        customer = customer,
                        onClick = {
                            showDialog.value = true
                            selectedCustomer = it
                        },
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

//                    HorizontalDivider(thickness = 2.dp)
                }
            }
        }

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                confirmButton = {
                    Button(onClick = {
                        viewModel.removeCustomer(queueId, selectedCustomer)
                        showDialog.value = false
                    }) {
                        Text("Remove")
                    }
                },
                title = { Text("Remove Customer?") },
                text = { Text("Are you sure you want to remove this customer from queue?") }
            )
        }
    }
}

@Composable
fun CustomerCard(
    index: Int,
    current: Int,
    waitTime: Long,
    customer: Customer,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {},
) {

    val dateTime = convertEpochToDateTime(waitTime)
    Card(
        modifier = modifier
            .fillMaxWidth()
            .size(100.dp),
        //shape = RectangleShape,
        onClick = { onClick(customer.id) }
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .matchParentSize()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$index",
                    modifier = Modifier.padding(horizontal = 2.dp),
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.LightGray,
                    fontStyle = FontStyle.Italic
                )

                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.weight(1f).padding(horizontal = 40.dp),
                ) {
                    Text(
                        text = customer.name,
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (index != 1) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Turn At:",
                            style = MaterialTheme.typography.labelMedium
                        )

                        Text(
                            text = dateTime.substringBefore(" "),
                            style = MaterialTheme.typography.bodyLarge,
                        )

                        Text(
                            text = dateTime.substringAfter(" ").substringBeforeLast(":"),
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color(0xFF1976D2),
                        )
                    }
                } else {
                    Text(
                        text = "Current",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(12.dp),
                        color = Color(0xFF1976D2)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomerCardPreview() {
    CustomerCard(
        index = 20,
        current = 15,
        waitTime = 1700412020310,
        customer = Customer(name = "Abhinav", age = 18, gender = Gender.MALE)
    )
}