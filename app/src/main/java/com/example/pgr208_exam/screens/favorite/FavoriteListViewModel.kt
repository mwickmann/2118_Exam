package com.example.pgr208_exam.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgr208_exam.database.repository.ProductRepository
import com.example.pgr208_exam.database.entities.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteListViewModel : ViewModel() {

    private val _favoriteProducts = MutableStateFlow<List<Product>>(emptyList())
    val favoriteProducts = _favoriteProducts.asStateFlow()

    fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfFavoriteIds = ProductRepository.getFavorites().map { it.productId }
            _favoriteProducts.value = ProductRepository.getProductsByIds(listOfFavoriteIds)
        }
    }
}