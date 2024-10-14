package com.example.pgr208_exam.screens.product_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pgr208_exam.screens.shopping_cart.CartListViewModel
import com.example.pgr208_exam.screens.ui_components.CategorySelector
import com.example.pgr208_exam.screens.ui_components.ProductItem

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel,
    viewModelCart: CartListViewModel,
    onProductClick: (productId: Int) -> Unit = {},
    navigateToFavoriteList: () -> Unit = {},
    navigateToCartList: () -> Unit = {},

) {
    val loading = viewModel.loading.collectAsState()
    val products = viewModel.products.collectAsState().value

    if (loading.value) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column {
            Spacer(modifier = Modifier.height(16.dp))

            // Toppseksjon med tittel og ikoner
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "FakeStore",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )

                IconButton(onClick = { viewModel.loadProducts(viewModel.selectedCategory.value) }) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh products")
                }
                IconButton(onClick = { navigateToFavoriteList() }) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorite", tint = Color.Black)
                }
                BadgeNumberWrapperView(
                    cartListViewModel = viewModelCart,
                    contentView = {
                        IconButton(
                            onClick = { navigateToCartList() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Cart",
                                tint = Color.Black
                            )
                        }

                    })
            }

            // Kategori-selektor
            val selectedCategory = viewModel.selectedCategory.collectAsState().value
            CategorySelector(
                categories = listOf("all items", "men's clothing", "women's clothing", "jewelery", "electronics"),
                selectedCategory = selectedCategory ?: "All Products",
                onCategorySelected = { category ->
                    if (category == "all items") {
                        viewModel.onCategorySelected(null)
                    } else {
                        viewModel.onCategorySelected(category)
                    }
                }
            )

            // LazyVerticalGrid for produktene, mer brukervennlig
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(products) { product ->
                    ProductItem(
                        product = product,
                        onClick = { onProductClick(product.id) }
                    )
                }
            }
        }
    }
}


@Composable
fun BadgeNumberWrapperView(
    cartListViewModel: CartListViewModel,
    contentView: @Composable () -> Unit,
    size: Dp = 20.dp,
    fontSize: TextUnit = 11.sp,
    padding: Dp = 4.dp
) {

    Box {
        contentView()
        val cartValues = cartListViewModel.cartProducts.collectAsState().value


        if (cartValues.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .size(size)
                    .clip(CircleShape)
                    .background(Color.Red)
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (cartValues.size > 99) ".." else cartValues.size.toString(),
                    color = Color.White,
                    fontSize = fontSize,
                    maxLines = 1
                )
            }
        }
    }
}

