package com.example.shopeasee_commercedemoapp.viewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopeasee_commercedemoapp.CartDataStoreManager
import com.example.shopeasee_commercedemoapp.data.model.CartProductModel
import com.example.shopeasee_commercedemoapp.data.model.ProductModel
import kotlinx.coroutines.launch

class CartViewModel(private val cartStore: CartDataStoreManager) : ViewModel() {

    private val _cartItems = mutableStateListOf<CartProductModel>()
    val cartItems: List<CartProductModel> = _cartItems

    init {
        viewModelScope.launch {
            loadCart()
        }
    }

    private suspend fun loadCart() {
        _cartItems.clear()
        _cartItems.addAll(cartStore.getCartItems())
    }

    fun addToCart(product: ProductModel, quantity: Int) {
        val existing = _cartItems.find { it.productModel.id == product.id }
        if (existing != null) {
            existing.quantity = quantity
        } else {
            _cartItems.add(CartProductModel(product, quantity))
        }

        // Save updated list
        viewModelScope.launch {
            cartStore.saveCartItems(_cartItems)
        }
    }


    fun clearCart() {
        _cartItems.clear()
        viewModelScope.launch {
            cartStore.clearCart()
        }
    }
    fun totalPrice():Double{
        var total =0.0
        cartItems.forEach{
            total +=(it.productModel.price*it.quantity)
        }
        return total
    }
}
