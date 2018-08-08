package com.toolslab.tractorlocator.di

import android.app.Application
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class AppModule {

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideResources(application: Application): Resources = application.applicationContext.resources

}
