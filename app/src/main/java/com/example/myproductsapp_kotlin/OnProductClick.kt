package com.example.myproductsapp_kotlin

import com.example.myproductsapp_kotlin.repository.Product

interface OnProductClick {
    fun onItemClick(product: Product)
    fun onButtonClick(product: Product)
}