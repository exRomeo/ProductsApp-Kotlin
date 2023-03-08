package com.example.myproductsapp_kotlin

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
    val api: API = retrofit.create(
        API::class.java
    )
}