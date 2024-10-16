package com.example.pgr208_exam.screens.order_history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.pgr208_exam.database.entities.OrderHistory

@Composable
fun OrderListItem(
    order: OrderHistory,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(10)
            )
            .background(color = Color.White)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${order.date}, ${order.time}",
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))

            var itemCount = 0
            var totalPrice = 0.0

            order.items.forEachIndexed { index, item ->
                itemCount++
                totalPrice += item.price
                Text(
                    text = "${index + 1}, ${item.title}",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Skriv ut antall items og total sum
            Text(
                text = "Quantity items: $itemCount",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )

            Text(
                text = "Total sum: $totalPrice $",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
        }
    }
}