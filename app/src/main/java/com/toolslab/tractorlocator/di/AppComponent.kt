package com.toolslab.tractorlocator.di

import android.app.Application
import com.toolslab.tractorlocator.TractorLocator
import com.toolslab.tractorlocator.base_repository.di.RepositoryComponent
import com.toolslab.tractorlocator.view.main.TractorLocatorModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class,
            ActivitiesBindingModule::class,
            TractorLocatorModule::class
        ],
        dependencies = [
            RepositoryComponent::class
        ]
)
interface AppComponent : AndroidInjector<TractorLocator> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(app: Application): Builder

        fun repositoryComponent(repositoryComponent: RepositoryComponent): Builder

        fun build(): AppComponent
    }
}
