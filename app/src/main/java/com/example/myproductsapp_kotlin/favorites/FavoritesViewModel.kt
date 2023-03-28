package com.example.myproductsapp_kotlin.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproductsapp_kotlin.repository.Product
import com.example.myproductsapp_kotlin.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repository: Repository) : ViewModel() {

    private var _productList = MutableStateFlow(listOf(Product()))
    val productsList = _productList.asStateFlow()

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().collect { _productList.value = it }
        }
    }

    fun removeFromFavorites(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFromFavorites(product)
        }
    }
}


