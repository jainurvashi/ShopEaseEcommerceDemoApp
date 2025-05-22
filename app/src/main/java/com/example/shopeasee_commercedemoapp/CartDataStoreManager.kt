package com.example.shopeasee_commercedemoapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.shopeasee_commercedemoapp.data.model.CartProductModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.flow.first

val Context.cartDataStore: DataStore<Preferences> by preferencesDataStore(name = "cart_data")

class CartDataStoreManager(private val context: Context) {
    private val dataStore = context.cartDataStore // ✅ use property delegate

    private val CART_KEY = stringPreferencesKey("cart_items")
    private val gson = Gson()

    suspend fun saveCartItems(items: List<CartProductModel>) {
        val json = gson.toJson(items)
        dataStore.edit { preferences ->
            preferences[CART_KEY] = json
        }
    }

    suspend fun getCartItems(): List<CartProductModel> {
        val preferences = dataStore.data.first()
        val json = preferences[CART_KEY] ?: return emptyList()
        return try {
            val type = object : TypeToken<List<CartProductModel>>() {}.type
            gson.fromJson(json, type)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun clearCart() {
        dataStore.edit { preferences ->
            preferences.remove(CART_KEY)
        }
    }
}
