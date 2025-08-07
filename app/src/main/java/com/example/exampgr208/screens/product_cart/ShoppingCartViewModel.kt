package com.example.exampgr208.screens.product_cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exampgr208.data.Order
import com.example.exampgr208.data.OrderHistory
import com.example.exampgr208.data.Product
import com.example.exampgr208.data.ProductRepository
import com.example.exampgr208.data.ShoppingCart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShoppingCartViewModel : ViewModel() {
    private val _shoppingCart = MutableStateFlow<List<Product>>(emptyList())
    val shoppingCartProducts = _shoppingCart.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _orderHistory = MutableStateFlow<List<OrderHistory>>(emptyList())
    val orderHistory = _orderHistory.asStateFlow()

    fun loadShoppingCart() {
        viewModelScope.launch {
            val listOfCartItems = ProductRepository.getShoppingCart()
            _loading.value = true

            val productList: List<Product> = listOfCartItems.map { it.product }.toList()

            _shoppingCart.value = productList
            _loading.value = false
        }
    }

    private suspend fun moveItemsToOrderHistory(cartItems: List<ShoppingCart>) {
        val orderTotal = cartItems.sumOf { it.product.price }

        val orderHistory = OrderHistory(
            orderDate = System.currentTimeMillis().toString(),
            productIds = cartItems.map { it.product.id },
            orderTotal = orderTotal
        )

        // Insert OrderHistory into the database and get the generated orderId
        val generatedOrderId = ProductRepository.insertOrderHistory(orderHistory)

        // If you want to update the _orderHistory.value with the new record, you can do it here
        val updatedOrderHistory = orderHistory.copy(orderId = generatedOrderId.toInt())

        _orderHistory.value = _orderHistory.value + updatedOrderHistory

        ProductRepository.clearShoppingCart()

        _shoppingCart.value = emptyList()
    }

    fun checkout() {
        viewModelScope.launch {
            val cartItems = ProductRepository.getShoppingCart()
            if (cartItems.isNotEmpty()) {
                moveItemsToOrderHistory(cartItems)
            }
        }
    }
}