package com.example.myproductsapp_kotlin

import android.graphics.drawable.AdaptiveIconDrawable
import android.icu.text.NumberFormat
import android.os.Build.VERSION_CODES.N
import java.util.*

class Product(
    val id: Int = 0,
    val title: String = "N/A",
    val description: String = "Please Select a Product From List",
    private val price: Int = 0,
    val discountPercentage: Float = 0.00f,
    val rating: Float = 0.00f,
    val stock: Int = 0,
    val brand: String = "N/A",
    val category: String = "N/A",
    val thumbnail: String = "N/A",
    val drawable: Int = 0,
    val images: List<String> = listOf<String>("N/A"),
) : java.io.Serializable {

    fun getTextPrice(): String {
        val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "US"))
        return currencyFormatter.format(price)
    }

    override fun equals(other: Any?): Boolean = this === other
}