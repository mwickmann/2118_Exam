package com.example.pgr208_exam.screens.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgr208_exam.database.repository.ProductRepository
import com.example.pgr208_exam.database.entities.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()


    init {
        loadProducts(null)
    }

    fun loadProducts(category: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _products.value = if (category == null) {
                ProductRepository.getProducts() // Henter alle produkter
            } else {
                ProductRepository.getProductsByCategory(category) // Filtrer produkter etter kategori
            }
            _loading.value = false
        }
    }

    fun onCategorySelected(category: String?) {
        _selectedCategory.value = category
        loadProducts(category)
    }
}

