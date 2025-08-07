package com.example.exampgr208.screens.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exampgr208.data.Favorite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.exampgr208.data.Product
import com.example.exampgr208.data.ProductRepository
import com.example.exampgr208.data.ShoppingCart
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel : ViewModel() {
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private val _isInShoppingCart = MutableStateFlow(false)
    val isInShoppingCart = _isInShoppingCart.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    fun setSelectedProduct(productId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _selectedProduct.value = ProductRepository.getProductById(productId)
            if (_selectedProduct.value != null) {
                _isFavorite.value = isProductFavorite()
                _isInShoppingCart.value = isProductInShoppingCart()
            }
            _loading.value = false
        }
    }

    fun loadProducts() {
        viewModelScope.launch {
            _loading.value = true
            _products.value = ProductRepository.getProducts()
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
            _isFavorite.value = isProductFavorite()
        }
    }

    fun updateShoppingCart(productId: Int) {
        viewModelScope.launch {
            val selectedProduct = selectedProduct.value
            if (selectedProduct != null) {
                val shoppingCart = ShoppingCart(productId, selectedProduct)
                if (isInShoppingCart.value) {
                    ProductRepository.removeCartItem(shoppingCart)
                } else {
                    ProductRepository.addCartItem(shoppingCart)
                }
                _isInShoppingCart.value = isProductInShoppingCart()
            }
        }
    }

    fun removeFromShoppingCart(productId: Int) {
        viewModelScope.launch {
            val selectedProduct = selectedProduct.value
            if (selectedProduct != null) {
                val shoppingCart = ShoppingCart(productId, selectedProduct)
                ProductRepository.removeCartItem(shoppingCart)
                _isInShoppingCart.value = isProductInShoppingCart()
            }
        }
    }

    private suspend fun isProductFavorite(): Boolean {
        val selectedProduct = selectedProduct.value
        return selectedProduct != null && ProductRepository.getFavorites().any { it.productId == selectedProduct.id }
    }

    private suspend fun isProductInShoppingCart(): Boolean {
        val selectedProduct = selectedProduct.value
        return selectedProduct != null && ProductRepository.getShoppingCart().any { it.productId == selectedProduct.id }
    }
}

