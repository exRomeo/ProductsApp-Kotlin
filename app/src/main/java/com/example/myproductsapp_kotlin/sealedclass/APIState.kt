package com.example.myproductsapp_kotlin.sealedclass

import com.example.myproductsapp_kotlin.repository.Product

sealed class APIState {
    class Success(val data: List<Product>) : APIState()
    class Failure(val msg: Throwable) : APIState()
    object Loading : APIState()
}
