package com.example.pgr208_exam.screens.shopping_cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgr208_exam.database.entities.Cart
import com.example.pgr208_exam.database.entities.OrderHistory
import com.example.pgr208_exam.database.entities.Product
import com.example.pgr208_exam.database.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class CartListViewModel : ViewModel() {

    private val _cartProducts = MutableStateFlow<List<Product>>(emptyList())
    val cartProducts = _cartProducts.asStateFlow()

    private val _isInCart = MutableStateFlow(false)
    val isInCart = _isInCart.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun placeOrder() {
        viewModelScope.launch {
            val cartProducts = _cartProducts.value
            if (cartProducts.isNotEmpty()) {
                val order = OrderHistory(
                    orderId = 0,
                    date = Date(),
                    time = System.currentTimeMillis().toDouble(),
                    items = cartProducts
                )
                cartProducts.forEach { product ->
                    updateCart(product.id)
                }
                ProductRepository.addOrder(order)

                _cartProducts.value = emptyList()
            }
        }
    }

    fun updateCart(id: Int) {
        viewModelScope.launch {
            Log.d("CartListViewModel", "updateCart called for cartId: $id")
            if (!isInCart.value) {
                ProductRepository.removeCart(Cart(id))
            } else {
                ProductRepository.addCart(Cart(id))
            }
            _isInCart.value = isCurrentProductACart()
            loadCarts()
        }
    }


    private suspend fun isCurrentProductACart(): Boolean {
        return ProductRepository.getCarts().any { it.id == selectedProduct.value?.id }
    }

    fun loadCarts() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfCartIds = ProductRepository.getCarts().map { it.id }
            _cartProducts.value = ProductRepository.getProductsByIds(listOfCartIds)
        }
    }
}