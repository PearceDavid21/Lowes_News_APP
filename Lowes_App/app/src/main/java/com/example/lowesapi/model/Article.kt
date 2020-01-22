package com.example.lowesapi.model

data class NewsResponse (val article: List<Article>)

data class Article (val author: String,
                    val title: String,
                    val urlToImage: String,
                    val url: String,
                    val source: Source)

data class Source (val name: String)