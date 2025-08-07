package com.example.exampgr208.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {

    @Query("SELECT * FROM Products")
    suspend fun getAllProducts(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(products: List<Product>)

    @Query("SELECT * FROM Products WHERE id = :productId")
    suspend fun getProductById(productId: Int): Product?

    @Query("SELECT * FROM Products WHERE id IN (:ids)")
    suspend fun getProductsById(ids: List<Int>): List<Product>
}
