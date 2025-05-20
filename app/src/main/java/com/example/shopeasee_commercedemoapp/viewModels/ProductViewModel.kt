package com.example.shopeasee_commercedemoapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopeasee_commercedemoapp.data.model.ProductModel
import com.example.shopeasee_commercedemoapp.data.repository.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _productList = MutableStateFlow<List<ProductModel>>(emptyList())
    val product: StateFlow<List<ProductModel>> = _productList
    private val _categoryList = MutableStateFlow<List<String>>(emptyList())
    val categoryList : StateFlow<List<String>> = _categoryList
    init {
        fetchProduct()
    }

    private fun fetchProduct() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAllProduct()
                _productList.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
    fun getCategories(){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCategories()
                _categoryList.value = response
            }catch (ex: Exception){
                ex.printStackTrace()
            }
        }
    }
}