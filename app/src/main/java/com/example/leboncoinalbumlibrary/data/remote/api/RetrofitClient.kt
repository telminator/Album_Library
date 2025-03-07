package com.example.leboncoinalbumlibrary.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://static.leboncoin.fr/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val albumApiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}