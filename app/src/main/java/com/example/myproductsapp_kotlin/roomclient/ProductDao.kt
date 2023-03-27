package com.example.myproductsapp_kotlin.roomclient

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.myproductsapp_kotlin.repository.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    suspend fun getOfflineProducts(): List<Product>

    @Query("SELECT * FROM product WHERE isFavorite = 1")
    fun getFavoritesList(): Flow<List<Product>>

    @Upsert
    suspend fun addProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product): Int
}
