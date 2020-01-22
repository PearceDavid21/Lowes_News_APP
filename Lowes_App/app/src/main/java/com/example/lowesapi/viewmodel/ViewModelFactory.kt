package com.example.lowesapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lowesapi.model.NewsDataManager

class ViewModelFactory (private val dataManager: NewsDataManager): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == NewsListViewModel::class.java) return NewsListViewModel(dataManager) as T
        throw RuntimeException("Cannot create an instance of $modelClass", ClassNotFoundException("Class not supported in ViewModelFactory"))
    }

}