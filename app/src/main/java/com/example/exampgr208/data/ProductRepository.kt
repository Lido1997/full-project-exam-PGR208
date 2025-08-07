package com.example.exampgr208.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductRepository {

    private val _httpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    private val _retrofit =
        Retrofit.Builder()
            .client(_httpClient)
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val _productService =
        _retrofit.create(ProductService::class.java)

    private lateinit var _appDatabase: AppDatabase

    fun initiateAppDatabase(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "appDatabase"
            ).fallbackToDestructiveMigration().build()
    }


    suspend fun getProducts(): List<Product> {
        try {
            val response = _productService.getAllProducts()

            if (response.isSuccessful) {
                val product = response.body()?.products ?: emptyList()

                _appDatabase.getProductDao().addProduct(product)

                return _appDatabase.getProductDao().getAllProducts()
            } else {
                throw Exception("getProducts: Error getting products")
            }
        } catch (e: Exception) {
            Log.e("getProducts","Failed to GET products from API", e)
            return _appDatabase.getProductDao().getAllProducts()
        }
    }

    suspend fun getProductById(productId: Int): Product? {
        return _appDatabase.getProductDao().getProductById(productId)
    }

    suspend fun getProductsById(ids: List<Int>): List<Product> {
        return _appDatabase.getProductDao().getProductsById(ids)
    }

    // -- Favorites --

    suspend fun getFavorites(): List<Favorite> {
        return _appDatabase.getFavoriteDao().getAllFavorites()
    }

    suspend fun addFavorite(favorite: Favorite) {
        return _appDatabase.getFavoriteDao().insertFavorite(favorite)
    }

    suspend fun removeFavorite(favorite: Favorite) {
        return _appDatabase.getFavoriteDao().removeFavorite(favorite)
    }

    // -- Shopping Cart --

    suspend fun getShoppingCart(): List<ShoppingCart> {
        return _appDatabase.getShoppingCartDao().getAllCartItems()
    }

    suspend fun addCartItem(shoppingCart: ShoppingCart) {
        return _appDatabase.getShoppingCartDao().insertCartItem(shoppingCart)
    }

    suspend fun removeCartItem(shoppingCart: ShoppingCart) {
        return _appDatabase.getShoppingCartDao().removeCartItem(shoppingCart)
    }

    suspend fun clearShoppingCart() {
        return _appDatabase.getShoppingCartDao().clearShoppingCart()
    }

    // -- Order History --

    suspend fun getOrderHistory(): List<OrderHistory> {
        return _appDatabase.getOrderHistoryDao().getOrderHistory()
    }

    suspend fun insertOrderHistory(orderHistory: OrderHistory): Long {
        return _appDatabase.getOrderHistoryDao().insertOrderHistory(orderHistory)
    }

    // -- Orders --
    suspend fun placeOrder(order: Order) {
        _appDatabase.getOrderDao().insertOrder(order)
    }

    suspend fun getOrders(): List<Order> {
        return _appDatabase.getOrderDao().getOrders()
    }

    suspend fun getOrderHistoryByIds(ids: List<Int>): List<OrderHistory> {
        return _appDatabase.getOrderHistoryDao().getOrderHistoryByIds(ids)
    }
}

