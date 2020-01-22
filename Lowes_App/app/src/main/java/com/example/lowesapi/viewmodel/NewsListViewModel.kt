package com.example.lowesapi.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lowesapi.R
import com.example.lowesapi.adapter.NewsAdapter
import com.example.lowesapi.model.Article
import com.example.lowesapi.model.NewsDataManager
import com.example.lowesapi.view.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsListViewModel(private val dataManager: NewsDataManager) : ViewModel() {
    private val disposables: CompositeDisposable = CompositeDisposable()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val newsListAdapter = NewsAdapter()
    val errorClickListener = View.OnClickListener{ loadNews(MainActivity.QUERY) }

    init {
        loadNews(MainActivity.QUERY)
    }

    private fun loadNews(country: String) {
        disposables.add(
            dataManager.getNewsHeadlines(country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveCharacterListStart() }
                .doAfterTerminate { onRetrieveCharacterListFinish() }
                .subscribe(
                    { result -> onRetrieveCharacterListSuccess(result as List<Article>) },
                    { onRetrievePostListError(it) }
                )
        )
    }

    override fun onCleared() {
        disposables.clear()
    }

    private fun onRetrieveCharacterListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveCharacterListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveCharacterListSuccess(postList:List<Article>) {
        newsListAdapter.updateNewsList(postList)
    }

    private fun onRetrievePostListError(throwable: Throwable) {
        throwable.printStackTrace()
        errorMessage.value = R.string.post_error
    }
}