package com.example.myproductsapp_kotlin

import com.example.myproductsapp_kotlin.repository.Product

interface OnProductClick {
    fun onClick(product: Product)
}