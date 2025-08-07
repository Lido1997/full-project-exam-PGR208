package com.example.exampgr208.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("/products")
    suspend fun getAllProducts(): Response<ProductListResponse>


    @GET("/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<Product>

}