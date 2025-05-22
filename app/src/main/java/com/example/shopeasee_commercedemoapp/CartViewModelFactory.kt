package com.example.shopeasee_commercedemoapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shopeasee_commercedemoapp.viewModels.CartViewModel

class CartViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val cartStore = CartDataStoreManager(context)
        return CartViewModel(cartStore) as T
    }
}
