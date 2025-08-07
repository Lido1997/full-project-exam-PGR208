package com.example.exampgr208.screens.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.exampgr208.data.Product
import com.example.exampgr208.data.ProductRepository
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _loading.value = true
            _products.value = ProductRepository.getProducts()
            _loading.value = false
        }
    }
}