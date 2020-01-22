package com.example.lowesapi.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("top-headlines")
    fun getNews(@Query("country") country:String, @Query("apiKey") apikey:String): Single<NewsResponse>


}