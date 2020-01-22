package com.example.lowesapi.injection

import android.app.Application
import android.content.Context
import com.example.lowesapi.R
import com.example.lowesapi.injection.component.AppComponent
import com.example.lowesapi.injection.component.DaggerAppComponent
import com.example.lowesapi.injection.module.ApiModule
import com.example.lowesapi.injection.module.AppModule
import com.example.lowesapi.injection.module.NetworkModule

class MVVMApplication : Application() {

    var component: AppComponent? = null
        get() {
            val apiUrl = getString(R.string.api_url)

            if (field == null) {
                component = DaggerAppComponent.builder()
                        .apiModule(ApiModule())
                        .networkModule(NetworkModule(applicationContext, apiUrl))
                        .appModule(AppModule(this))
                        .build()
            }
            return field
        }

    companion object {

        operator fun get(context: Context): MVVMApplication {
            return context.applicationContext as MVVMApplication
        }
    }

}