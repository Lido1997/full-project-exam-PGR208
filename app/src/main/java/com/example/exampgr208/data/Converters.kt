package com.example.exampgr208.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun productToString(product: Product): String {
        return Gson().toJson(product)
    }

    @TypeConverter
    fun stringToProduct(json: String): Product {
        val type = object : TypeToken<Product>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun productListToString(products: List<Product>): String {
        return Gson().toJson(products)
    }

    @TypeConverter
    fun stringToProductList(json: String): List<Product> {
        val type = object : TypeToken<List<Product>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toString(value: List<Int>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun fromStringToInt(value: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }
}