package com.toolslab.tractorlocator.di

import com.toolslab.tractorlocator.view.main.TractorLocatorActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBindingModule {

    @ContributesAndroidInjector
    abstract fun tractorLocatorActivity(): TractorLocatorActivity

}
