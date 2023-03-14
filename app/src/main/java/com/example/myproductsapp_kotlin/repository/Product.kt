package com.example.myproductsapp_kotlin.repository

import android.icu.text.NumberFormat
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "product")
class Product(
    @PrimaryKey
    val id: Int = 0,
    val title: String = "N/A",
    val description: String = "Please Select a Product From List",
    val price: Int = 0,
    val discountPercentage: Float = 0.00f,
    val rating: Float = 0.00f,
    val stock: Int = 0,
    val brand: String = "N/A",
    val category: String = "N/A",
    val thumbnail: String = "N/A",
    var isFavorite: Boolean = false
) : java.io.Serializable {

    fun getTextPrice(): String {
        val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("en", "US"))
        return currencyFormatter.format(price)
    }

    override fun equals(other: Any?): Boolean = this === other
    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + price
        result = 31 * result + discountPercentage.hashCode()
        result = 31 * result + rating.hashCode()
        result = 31 * result + stock
        result = 31 * result + brand.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + thumbnail.hashCode()
        result = 31 * result + isFavorite.hashCode()
        return result
    }

}