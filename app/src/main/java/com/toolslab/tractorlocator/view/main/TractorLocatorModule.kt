package com.toolslab.tractorlocator.view.main

import dagger.Module
import dagger.Provides

@Module
class TractorLocatorModule {

    @Provides
    fun providePresenter(tractorLocatorPresenter: TractorLocatorPresenter): TractorLocatorContract.Presenter = tractorLocatorPresenter

}
