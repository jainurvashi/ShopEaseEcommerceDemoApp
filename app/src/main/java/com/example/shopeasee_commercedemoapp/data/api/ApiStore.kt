package com.example.shopeasee_commercedemoapp.data.api

import com.example.shopeasee_commercedemoapp.data.model.ProductModel
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiStore {
    @GET("products")
    suspend fun getAllProduct(): List<ProductModel>
    @GET("products/{id}")
    suspend fun getProductDetails(@Path("id") productId: Int): ProductModel
    @GET("products/categories")
    suspend fun getCategories():List<String>

}