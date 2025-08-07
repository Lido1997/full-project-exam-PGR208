package com.example.exampgr208.screens.product_favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exampgr208.data.Product
import com.example.exampgr208.data.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {
    private val _favoriteProducts = MutableStateFlow<List<Product>>(emptyList())
    val favoriteProducts = _favoriteProducts.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()


    fun loadFavorites() {
        viewModelScope.launch {
            val listOfFavoriteId = ProductRepository.getFavorites().map { it.productId }
            _loading.value = true
            _favoriteProducts.value = ProductRepository.getProductsById(listOfFavoriteId)
            _loading.value = false
        }
    }
}