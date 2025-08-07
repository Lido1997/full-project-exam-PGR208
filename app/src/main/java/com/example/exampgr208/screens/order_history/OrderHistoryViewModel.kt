package com.example.exampgr208.screens.order_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exampgr208.data.OrderHistory
import com.example.exampgr208.data.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderHistoryViewModel : ViewModel() {
    private val _orderHistory = MutableStateFlow<List<OrderHistory>>(emptyList())
    val orderHistory = _orderHistory.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun loadOrderHistory() {
        viewModelScope.launch {
            _loading.value = true
            _orderHistory.value = ProductRepository.getOrderHistory()
            _loading.value = false
        }
    }

    fun addOrder(orderHistory: OrderHistory) {
        viewModelScope.launch {
            ProductRepository.insertOrderHistory(orderHistory)
        }
    }
}