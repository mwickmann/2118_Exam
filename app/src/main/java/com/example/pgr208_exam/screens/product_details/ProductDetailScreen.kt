package com.example.pgr208_exam.screens.product_details


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pgr208_exam.screens.product_list.BadgeNumberWrapperView
import com.example.pgr208_exam.screens.shopping_cart.CartListViewModel

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailsViewModel,
    viewModelCart: CartListViewModel,
    navigateToCartList: () -> Unit = {},
    onBackButtonClick: () -> Unit = {},

    ) {
    val loading = viewModel.loading.collectAsState()
    val productState = viewModel.selectedProduct.collectAsState()
    val isFavorite = viewModel.isFavorite.collectAsState()
    val product = productState.value

    if (loading.value) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {

            Column {
                Spacer(modifier = Modifier.height(18.dp))
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    model = product?.imageUrl ?: "",
                    contentDescription = "Image of ${product?.title}",
                )
                Spacer(modifier = Modifier.height(8.dp))


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())

                ) {
                    IconButton(
                        onClick = { onBackButtonClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate BACK"
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = product?.title ?: "",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )


                    Text(
                        text = "Price: $${product?.price ?: 0.0}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Spacer(modifier = Modifier.height(8.dp))


                    Text(
                        text = "Description:\n${product?.description ?: ""}",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    product?.rating?.let { rating ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Rating: ${rating.rate} ",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )

                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFDAA520),
                                modifier = Modifier.size(16.dp)
                            )

                            Text(
                                text = " (${rating.count} reviews)",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        }
                    }

                    Button(
                        onClick = { product?.id?.let { viewModel.updateCart(it) } },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text(text = "Add to Cart")
                    }
                }
            }

            IconButton(
                onClick = { viewModel.updateFavorite(product?.id ?: 0) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Toggle Favorite",
                    tint = if (isFavorite.value) Color.Red else MaterialTheme.colorScheme.onSurface
                )
            }
            // oppdateres slik at tall i antall produkter i handlekurven oppdateres

            BadgeNumberWrapperView(
                cartListViewModel = viewModelCart,
                contentView = {
                    IconButton(
                        onClick = { navigateToCartList() },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)

                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = Color.Black
                        )
                    }
                },

            )
        }
    }
}

