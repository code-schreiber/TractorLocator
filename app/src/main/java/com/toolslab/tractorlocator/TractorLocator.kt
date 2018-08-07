package com.toolslab.tractorlocator

import com.toolslab.tractorlocator.base_repository.di.DaggerRepositoryComponent
import com.toolslab.tractorlocator.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree

class TractorLocator : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val repositoryComponent = DaggerRepositoryComponent.builder().build()
        return DaggerAppComponent
                .builder()
                .create(this)
                .repositoryComponent(repositoryComponent)
                .build()
    }

}
