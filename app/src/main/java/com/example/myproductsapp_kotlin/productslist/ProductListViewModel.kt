package com.example.myproductsapp_kotlin.productslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproductsapp_kotlin.repository.Product
import com.example.myproductsapp_kotlin.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListViewModel(private val repository: Repository) : ViewModel() {
    private var _productsList = MutableLiveData<List<Product>>()
    val productsList: LiveData<List<Product>> = _productsList

    fun getOnlineList() {
        viewModelScope.launch(Dispatchers.IO) {
            _productsList.postValue(
                repository.getAllProducts().body()?.products ?: listOf(Product())
            )
        }
    }

    fun getOfflineList() {
        viewModelScope.launch(Dispatchers.IO) { _productsList.postValue(repository.getOfflineData()) }
    }

    fun addFavorite(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToFavorites(product)
            getOfflineList()
        }
    }

    fun removeFavorite(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFromFavorites(product)
            getOfflineList()
        }
    }

    fun checkConnection(): Boolean = repository.checkConnection()
}