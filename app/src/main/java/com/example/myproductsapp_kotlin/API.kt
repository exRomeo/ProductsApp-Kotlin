package com.example.myproductsapp_kotlin

import retrofit2.Call
import retrofit2.http.GET

const val BASE_URL = "https://dummyjson.com/"

interface API {
    @GET("products")
    fun getAllProducts(): Call<ProductModel>
}