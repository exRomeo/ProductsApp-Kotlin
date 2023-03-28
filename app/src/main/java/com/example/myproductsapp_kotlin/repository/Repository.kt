package com.example.myproductsapp_kotlin.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.myproductsapp_kotlin.retrofitclient.RetrofitClient
import com.example.myproductsapp_kotlin.roomclient.RoomClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(private val context: Context) {

    private val dao by lazy { RoomClient.getInstance(context).getProductDao() }


    fun getAllProducts(): Flow<List<Product>> {
        return flow {
            val response = RetrofitClient.api.getAllProducts()
            if (response.isSuccessful && response.body() != null && response.body()!!.products != null)
                emit(response.body()!!.products!!)
            else throw Exception("Timeout Couldn't fetch data")
        }

    }

    fun getOfflineData(): Flow<List<Product>> {
        return dao.getOfflineProducts()
    }

    suspend fun addToFavorites(product: Product) {
        dao.addProduct(product.copy(isFavorite = true))
    }

    suspend fun removeFromFavorites(product: Product) {
        dao.addProduct(product.copy(isFavorite = false))
    }

    fun getFavorites(): Flow<List<Product>> {
        return dao.getFavoritesList()
    }

    fun checkConnection(): Boolean {
        val connManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork =
            connManager.getNetworkCapabilities(connManager.activeNetwork) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

}