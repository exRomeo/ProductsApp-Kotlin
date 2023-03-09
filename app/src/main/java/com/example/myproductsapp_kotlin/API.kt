package com.example.myproductsapp_kotlin

import retrofit2.Response
import retrofit2.http.GET


interface API {
    @GET("products")
    suspend fun getAllProducts(): Response<ProductModel>
}