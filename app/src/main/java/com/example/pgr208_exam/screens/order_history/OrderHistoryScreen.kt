package com.example.pgr208_exam.screens.order_history


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
@Composable
fun OrderHistoryScreen(
    viewModel: OrderHistoryViewModel,
    navigateToHome: () -> Unit = {},
) {
    val orders = viewModel.orderHistory.collectAsState()

    Column(modifier = Modifier.fillMaxSize()
        .navigationBarsPadding()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Order History",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Divider()

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(orders.value) { order ->
                OrderListItem(order = order)
            }
        }

        Button(
            onClick = { navigateToHome() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Text("Home")
        }
    }
}
