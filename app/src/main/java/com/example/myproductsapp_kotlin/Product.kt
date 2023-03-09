package com.example.myproductsapp_kotlin

import android.icu.text.NumberFormat
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "product")
class Product(
    @PrimaryKey
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
    @Ignore
    val images: List<String> = listOf<String>("N/A"),
) : java.io.Serializable {

    fun getTextPrice(): String {
        val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "US"))
        return currencyFormatter.format(price)
    }

    override fun equals(other: Any?): Boolean = this === other
}