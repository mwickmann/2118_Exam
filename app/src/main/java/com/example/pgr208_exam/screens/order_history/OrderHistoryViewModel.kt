package com.example.pgr208_exam.screens.order_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgr208_exam.database.repository.ProductRepository
import com.example.pgr208_exam.database.entities.OrderHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class OrderHistoryViewModel : ViewModel() {

    private val _orderHistory = MutableStateFlow<List<OrderHistory>>(emptyList())
    val orderHistory = _orderHistory.asStateFlow()

    fun loadOrderHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfOrderHistoryIds = ProductRepository.getOrders().map { it.orderId }
            val newOrders = ProductRepository.getOrdersByIds(listOfOrderHistoryIds)

            _orderHistory.value = newOrders
        }
        }
    }