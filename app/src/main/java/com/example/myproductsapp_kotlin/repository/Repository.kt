package com.example.myproductsapp_kotlin.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.myproductsapp_kotlin.retrofitclient.RetrofitClient
import com.example.myproductsapp_kotlin.roomclient.RoomClient
import retrofit2.Response

class Repository(private val context: Context) {

    private val dao by lazy { RoomClient.getInstance(context).getProductDao() }


    suspend fun getAllProducts(): Response<ProductModel> {
        return RetrofitClient.api.getAllProducts()
    }

    suspend fun getOfflineData(): List<Product> {
        return dao.getOfflineProducts()
    }

    suspend fun addToFavorites(product: Product) {
        product.isFavorite = true
        dao.addProduct(product)
    }

    suspend fun removeFromFavorites(product: Product) {
        product.isFavorite = false
        dao.addProduct(product)
    }

    fun getFavorites(): List<Product> {
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