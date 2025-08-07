package com.example.exampgr208.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteProducts")
data class Favorite(
    @PrimaryKey
    val productId: Int,
)