package com.example.lowesapi.injection.component

import com.example.lowesapi.injection.PerActivity
import com.example.lowesapi.injection.module.ActivityModule
import com.example.lowesapi.view.MainActivity

import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(main2Activity: MainActivity)
}