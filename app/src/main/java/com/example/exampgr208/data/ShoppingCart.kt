package com.example.exampgr208.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ShoppingCart")
data class ShoppingCart(
    @PrimaryKey
    val productId: Int,
    val product: Product
)