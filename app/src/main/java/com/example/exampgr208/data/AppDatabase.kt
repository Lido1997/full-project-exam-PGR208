package com.example.exampgr208.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Product::class, Favorite::class, ShoppingCart::class, OrderHistory::class, Order::class],
    version = 8,
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    abstract fun getFavoriteDao(): FavoriteDao
    abstract fun getShoppingCartDao(): ShoppingCartDao
    abstract fun getOrderHistoryDao(): OrderHistoryDao
    abstract fun getOrderDao(): OrderDao
}