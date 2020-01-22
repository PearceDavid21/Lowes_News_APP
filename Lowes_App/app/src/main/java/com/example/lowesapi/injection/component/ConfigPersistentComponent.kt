package com.example.lowesapi.injection.component

import com.example.lowesapi.injection.ConfigPersistent
import com.example.lowesapi.injection.module.ActivityModule
import dagger.Component

@ConfigPersistent
@Component(dependencies = [AppComponent::class])
interface ConfigPersistentComponent {

    fun activityComponent(activityModule: ActivityModule): ActivityComponent
}
