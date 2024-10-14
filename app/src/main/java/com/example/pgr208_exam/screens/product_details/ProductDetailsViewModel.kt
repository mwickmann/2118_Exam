package com.example.pgr208_exam.screens.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgr208_exam.database.entities.Cart
import com.example.pgr208_exam.database.repository.ProductRepository
import com.example.pgr208_exam.database.entities.Favorite
import com.example.pgr208_exam.database.entities.Product
import com.example.pgr208_exam.database.entities.Rating
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private val _isCart = MutableStateFlow(false)
    val isCart = _isCart.asStateFlow()

    private val _isOrderHistory = MutableStateFlow(false)
    val isOrderHistory = _isOrderHistory.asStateFlow()


    private val _productRating = MutableStateFlow<Rating?>(null)
    val productRating: StateFlow<Rating?> = _productRating

    fun setSelectedProduct(productId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _selectedProduct.value = ProductRepository.getProductById(productId)
            _isFavorite.value = isCurrentProductAFavorite()
            _isCart.value = isCurrentInCart()
            // _isOrderHistory.value = isOrderInHistory(productId)
            _loading.value = false
        }
    }

    fun updateFavorite(productId: Int) {
        viewModelScope.launch {
            if (isFavorite.value) {
                ProductRepository.removeFavorite(Favorite(productId))
            } else {
                ProductRepository.addFavorite(Favorite(productId))
            }
            _isFavorite.value = isCurrentProductAFavorite()
        }
    }


    fun updateCart(id: Int) {
        viewModelScope.launch {
            if (isCart.value) {
                ProductRepository.removeCart(Cart(id))
            } else {
                ProductRepository.addCart(Cart(id))
            }
            _isCart.value = isCurrentInCart()
        }
    }

    private suspend fun isCurrentProductAFavorite(): Boolean {
        return ProductRepository.getFavorites()
            .any { it.productId == selectedProduct.value?.id }
    }

    private suspend fun isCurrentInCart(): Boolean {
        return ProductRepository.getCarts()
            .any { it.id == selectedProduct.value?.id }
    }
}

