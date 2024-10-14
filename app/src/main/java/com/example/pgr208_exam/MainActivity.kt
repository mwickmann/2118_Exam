package com.example.pgr208_exam

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pgr208_exam.database.repository.ProductRepository
import com.example.pgr208_exam.screens.ui_components.BottomNavItem
import com.example.pgr208_exam.screens.ui_components.CustomScaffold
import com.example.pgr208_exam.screens.favorite.FavoriteListScreen
import com.example.pgr208_exam.screens.favorite.FavoriteListViewModel
import com.example.pgr208_exam.screens.order_history.OrderHistoryScreen
import com.example.pgr208_exam.screens.order_history.OrderHistoryViewModel
import com.example.pgr208_exam.screens.product_details.ProductDetailScreen
import com.example.pgr208_exam.screens.product_details.ProductDetailsViewModel
import com.example.pgr208_exam.screens.product_list.ProductListScreen
import com.example.pgr208_exam.screens.product_list.ProductListViewModel
import com.example.pgr208_exam.screens.shopping_cart.CartListScreen
import com.example.pgr208_exam.screens.shopping_cart.CartListViewModel
import com.example.pgr208_exam.ui.theme.PGR208_ExamTheme



class MainActivity : ComponentActivity() {

    private val _productListViewModel: ProductListViewModel by viewModels()
    private val _favoriteListViewModel: FavoriteListViewModel by viewModels()
    private val _productDetailsViewModel: ProductDetailsViewModel by viewModels()
    private val _orderHistoryViewModel: OrderHistoryViewModel by viewModels()
    private val _cartListViewModel: CartListViewModel by viewModels()

    @SuppressLint("ComposableDestinationInComposeScope", "UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ProductRepository.initiateDatabase(applicationContext)
        setContent {
            PGR208_ExamTheme {

                val navController = rememberNavController()
                CustomScaffold(navController = navController) { paddingValues ->
                    CustomScaffold(
                        navController = navController,
                        items = listOf(
                            BottomNavItem.ProductList,
                            BottomNavItem.FavoriteList,
                            BottomNavItem.ShoppingCart,
                            BottomNavItem.OrderHistory
                        )
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = "productListScreen"
                        ) {
                            composable(route = "productListScreen") {
                                ProductListScreen(
                                    viewModel = _productListViewModel,
                                    viewModelCart = _cartListViewModel,
                                    onProductClick = { productId ->
                                        navController.navigate("productDetailsScreen/$productId")
                                    },
                                    navigateToFavoriteList = {
                                        navController.navigate("favoriteListScreen")
                                    },
                                    navigateToCartList = {
                                        navController.navigate("cartListScreen")
                                    },
                                )
                            }
                            composable(
                                route = "productDetailsScreen/{productId}",
                                arguments = listOf(
                                    navArgument(name = "productId") {
                                        type = NavType.IntType
                                    }
                                )
                            ) { backStackEntry ->
                                val productId = backStackEntry.arguments?.getInt("productId") ?: -1
                                LaunchedEffect(productId) {
                                    _productDetailsViewModel.setSelectedProduct(productId)
                                }
                                ProductDetailScreen(
                                    viewModel = _productDetailsViewModel,
                                    viewModelCart = _cartListViewModel,
                                    onBackButtonClick = { navController.popBackStack() },

                                    )
                            }
                            composable(route = "favoriteListScreen") {
                                LaunchedEffect(Unit) {
                                    _favoriteListViewModel.loadFavorites()
                                }
                                FavoriteListScreen(
                                    viewModel = _favoriteListViewModel,
                                    onBackButtonClick = { navController.popBackStack() },
                                    onProductClick = { productId ->
                                        navController.navigate("productDetailsScreen/$productId")
                                    }
                                )
                            }
                            composable(route = "cartListScreen") {
                                LaunchedEffect(Unit) {
                                    _cartListViewModel.loadCarts()
                                }
                                CartListScreen(
                                    viewModelCart = _cartListViewModel,
                                    onBackButtonClick = { navController.popBackStack() },
                                    onProductClick = { id ->
                                        navController.navigate("productDetailsScreen/$id")
                                    },
                                    navigateToOrderHistory = {
                                        navController.navigate("orderHistory")
                                    }
                                )
                            }
                            composable(route = "orderHistory") {
                                LaunchedEffect(Unit) {
                                    _orderHistoryViewModel.loadOrderHistory()
                                }
                                OrderHistoryScreen(
                                    viewModel = _orderHistoryViewModel,
                                    navigateToHome = { navController.navigate("productListScreen") }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}