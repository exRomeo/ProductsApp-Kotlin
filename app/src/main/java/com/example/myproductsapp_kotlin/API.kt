package com.example.myproductsapp_kotlin

import retrofit2.Call
import retrofit2.http.GET



interface API {
    @GET("products")
    fun getAllProducts(): Call<ProductModel>
}