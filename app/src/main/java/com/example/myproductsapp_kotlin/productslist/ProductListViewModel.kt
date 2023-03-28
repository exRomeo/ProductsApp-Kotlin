package com.example.myproductsapp_kotlin.productslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproductsapp_kotlin.repository.Product
import com.example.myproductsapp_kotlin.repository.Repository
import com.example.myproductsapp_kotlin.sealedclass.APIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProductListViewModel(private val repository: Repository) : ViewModel() {
    private var _productList = MutableStateFlow<APIState>(APIState.Loading)
    val productsList = _productList.asStateFlow()

    init {
        updateList()
    }

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

    private fun getOnlineList() {
        viewModelScope.launch {
            repository.getAllProducts().catch {
                _productList.value = APIState.Failure(it) }
                .collect { _productList.value = APIState.Success(it) }
        }
    }

    private fun getOfflineList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getOfflineData().collect { _productList.value = APIState.Success(it) }
        }
    }

    private fun checkConnection(): Boolean = repository.checkConnection()

    private fun updateList() {

        if (checkConnection())
            getOnlineList()
        else
            getOfflineList()
    }
}