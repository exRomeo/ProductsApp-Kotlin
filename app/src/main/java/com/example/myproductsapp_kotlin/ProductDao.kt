package com.example.myproductsapp_kotlin

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    suspend fun getOfflineProducts():List<Product>
    @Upsert
    suspend fun addProduct(product: Product)
    @Delete
    suspend fun deleteProduct(product: Product):Int
}