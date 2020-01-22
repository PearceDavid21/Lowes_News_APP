package com.example.lowesapi.model

import com.example.lowesapi.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {
    val apiResponse= Retrofit.Builder()
        .baseUrl(Constants.apiURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsAPI::class.java)
}