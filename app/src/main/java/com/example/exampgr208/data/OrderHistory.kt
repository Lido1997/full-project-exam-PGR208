package com.example.exampgr208.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "OrderHistory")
data class OrderHistory(
    @PrimaryKey (autoGenerate = true)
    val orderId: Int = 0,
    val orderDate: String,
    val orderTotal: Double,
    @ColumnInfo(name = "productIds")
    val productIds: List<Int>
)