package com.example.exampgr208.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Long = 0,
    val orderDate: String,
    @ColumnInfo(name = "productIds")
    val productIds: List<Int>,
    val orderTotal: Double
)