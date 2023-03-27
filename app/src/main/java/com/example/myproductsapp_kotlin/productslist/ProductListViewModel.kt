package com.example.myproductsapp_kotlin.productslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproductsapp_kotlin.repository.Product
import com.example.myproductsapp_kotlin.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ProductListViewModel(private val repository: Repository) : ViewModel() {
    val productsList by lazy { updateList() }

    fun addFavorite(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToFavorites(product)
        }
    }

    fun removeFavorite(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFromFavorites(product)
        }
    }

    private fun getOnlineList(): Flow<List<Product>> {
        return flow { repository.getAllProducts().body()?.products?.let { emit(it) } }
    }

    private fun getOfflineList(): Flow<List<Product>> {
        return repository.getOfflineData()
    }

    private fun checkConnection(): Boolean = repository.checkConnection()

    fun updateList(): Flow<List<Product>> {
        return if (checkConnection())
            getOnlineList()
        else
            getOfflineList()
    }
}