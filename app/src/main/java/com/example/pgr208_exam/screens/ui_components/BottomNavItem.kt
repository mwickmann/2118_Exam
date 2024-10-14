package com.example.pgr208_exam.screens.ui_components

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

    sealed class BottomNavItem(var icon: ImageVector, var route: String) {
        object ProductList : BottomNavItem(Icons.Filled.Home, "productListScreen")
        object FavoriteList : BottomNavItem(Icons.Filled.Favorite, "favoriteListScreen")
        object ShoppingCart : BottomNavItem(Icons.Filled.ShoppingCart, "cartListScreen")
        object OrderHistory : BottomNavItem(Icons.Filled.DateRange, "orderHistory")
    }

