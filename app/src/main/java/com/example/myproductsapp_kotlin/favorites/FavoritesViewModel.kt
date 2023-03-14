package com.example.myproductsapp_kotlin.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproductsapp_kotlin.repository.Product
import com.example.myproductsapp_kotlin.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repository: Repository) : ViewModel() {
    private var _productsList = MutableLiveData<List<Product>>()
    val productsList: LiveData<List<Product>> = _productsList

    fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            _productsList.postValue(
                repository.getFavorites()
            )
        }
    }

    fun removeFromFavorites(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFromFavorites(product)
            getFavorites()
        }
    }
}


