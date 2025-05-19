package com.example.shopeasee_commercedemoapp.data.api

import com.example.shopeasee_commercedemoapp.data.model.ProductModel
import retrofit2.http.GET

interface ApiStore {
    @GET("products")
    suspend fun getAllProduct(): List<ProductModel>

}