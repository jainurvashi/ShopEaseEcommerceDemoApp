package com.example.shopeasee_commercedemoapp.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopeasee_commercedemoapp.data.model.ProductModel
import com.example.shopeasee_commercedemoapp.data.repository.RetrofitInstance
import kotlinx.coroutines.launch
import androidx.compose.runtime.State

class DetailedViewModel: ViewModel() {
    private val _productDetail = mutableStateOf<ProductModel?>(null)
    val productDetail: State<ProductModel?> = _productDetail

    fun getDetail(productId: Int){
       viewModelScope.launch {
           try{
               val response = RetrofitInstance.api.getProductDetails(productId)
               _productDetail.value = response
           }catch (ex: Exception){
               ex.printStackTrace()
           }

       }

    }


}