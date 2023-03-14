package com.example.myproductsapp_kotlin.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myproductsapp_kotlin.repository.Repository

class FavoritesViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) FavoritesViewModel(
            repository
        ) as T else throw java.lang.IllegalArgumentException("ViewModel Class Not Found!!!")
    }
}