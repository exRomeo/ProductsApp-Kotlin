package com.example.myproductsapp_kotlin.retrofitclient

import com.example.myproductsapp_kotlin.repository.ProductModel
import retrofit2.Response
import retrofit2.http.GET

interface API {
    @GET("products")
    suspend fun getAllProducts(): Response<ProductModel>
}