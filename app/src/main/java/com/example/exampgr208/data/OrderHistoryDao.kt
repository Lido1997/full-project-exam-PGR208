package com.example.exampgr208.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderHistoryDao {

    @Query("SELECT * FROM OrderHistory")
    suspend fun getOrderHistory(): List<OrderHistory>

    @Insert
    suspend fun insertOrderHistory(orderHistory: OrderHistory): Long

    @Delete
    suspend fun deleteOrderHistory(orderHistory: OrderHistory)

    @Query("SELECT * FROM OrderHistory WHERE orderId IN (:orderIds)")
    suspend fun getOrderHistoryByIds(orderIds: List<Int>): List<OrderHistory>
}
