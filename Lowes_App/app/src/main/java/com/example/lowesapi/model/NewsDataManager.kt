package com.example.lowesapi.model

import com.example.lowesapi.util.Constants
import io.reactivex.Single

class NewsDataManager(private val newsAPI: NewsAPI) {
    fun getNewsHeadlines(country:String): Single<List<Article>> {
        return newsAPI.getNews(country, Constants.apiKey).map(NewsResponse::article)
    }
}