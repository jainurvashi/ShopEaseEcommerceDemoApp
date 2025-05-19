package com.example.shopeasee_commercedemoapp.data.repository

import com.example.shopeasee_commercedemoapp.data.api.ApiStore
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: ApiStore by lazy{
        Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiStore::class.java)
    }
}