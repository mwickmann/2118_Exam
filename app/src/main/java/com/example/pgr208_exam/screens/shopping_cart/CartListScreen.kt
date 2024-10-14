package com.example.pgr208_exam.screens.shopping_cart

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.pgr208_exam.database.entities.Product
import com.example.pgr208_exam.screens.ui_components.ProductItem

@Composable
fun CartListScreen(
    viewModelCart: CartListViewModel,
    onBackButtonClick: () -> Unit = {},
    onProductClick: (productId: Int) -> Unit = {},
    navigateToOrderHistory: () -> Unit = {}
) {
    val products = viewModelCart.cartProducts.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { onBackButtonClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate Back"
                        )
                    }
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Cart",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                // Refresh Button
                IconButton(
                    onClick = { viewModelCart.loadCarts() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh products"
                    )
                }
            }
        }

        item {
            Divider()
        }

        items(products.value) { product ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Delete Button
                IconButton(
                    onClick = {
                        Log.d("CartListScreen", "Clicked on close button for cartId: ${product.id}")
                        viewModelCart.updateCart(product.id)
                    }
                ) {
                    Icon(
                        modifier = Modifier.padding(8.dp),
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Product",
                        tint = Color.Black
                    )
                }
                ProductItem(
                    product = product,
                    onClick = {
                        onProductClick(product.id)
                    }
                )
            }
            Divider()
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total: ${calculateTotalPrice(products.value)} $",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize() // Gjør at Box tar opp hele tilgjengelig plass
                ) {
                    Button(
                        onClick = {
                            viewModelCart.placeOrder()
                            navigateToOrderHistory()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text("Place Order")
                    }
                }
            }
        }
    }

fun calculateTotalPrice(products: List<Product>): Double {
    return products.sumOf { it.price }
}