package com.example.exampgr208.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDao {

    @Insert
    suspend fun insertOrder(order: Order)

    @Query("SELECT * FROM Orders")
    suspend fun getOrders(): List<Order>
}