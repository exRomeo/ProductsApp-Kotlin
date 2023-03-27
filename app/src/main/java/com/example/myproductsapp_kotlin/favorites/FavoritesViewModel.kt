package com.example.myproductsapp_kotlin.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproductsapp_kotlin.repository.Product
import com.example.myproductsapp_kotlin.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoritesViewModel(private val repository: Repository) : ViewModel() {

    val productsList by lazy { repository.getFavorites() }

    fun removeFromFavorites(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFromFavorites(product)
        }
    }
}


