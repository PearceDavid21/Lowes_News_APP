package com.example.lowesapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lowesapi.model.Article
import org.greenrobot.eventbus.EventBus

class ArticleViewModel : ViewModel() {
    val articleImage = MutableLiveData<String>()
    val author = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val source = MutableLiveData<String>()
    val url = MutableLiveData<String>()

    fun bind(article : Article){
        articleImage.value = article.urlToImage
        author.value = article.author
        title.value = article.title
        source.value = article.source.name
        url.value = article.url
    }

    fun openArticleDetail(){
        EventBus.getDefault().post(url.value)
    }
}