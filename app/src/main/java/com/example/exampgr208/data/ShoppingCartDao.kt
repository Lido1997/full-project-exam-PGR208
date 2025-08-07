package com.example.exampgr208.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShoppingCartDao {
    @Query("SELECT * FROM ShoppingCart")
    suspend fun getAllCartItems(): List<ShoppingCart>

    @Insert
    suspend fun insertCartItem(cartItem: ShoppingCart)

    @Delete
    suspend fun removeCartItem(cartItem: ShoppingCart)

    @Query("DELETE FROM ShoppingCart")
    suspend fun clearShoppingCart()
}