package com.example.lowesapi.injection.component

import android.app.Application
import android.content.Context
import com.example.lowesapi.injection.ApplicationContext
import com.example.lowesapi.injection.module.AppModule
import com.example.lowesapi.model.NewsDataManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun apiManager(): NewsDataManager
}