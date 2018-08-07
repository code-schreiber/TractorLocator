package com.toolslab.tractorlocator.view.main

import android.support.annotation.CheckResult
import com.toolslab.tractorlocator.base_repository.Repository
import javax.inject.Inject

class TractorLocatorInteractor @Inject constructor() {

    @Inject
    internal lateinit var repository: Repository

    @CheckResult
    internal fun listFields() = repository.listFields()

    @CheckResult
    internal fun listDrivers() = repository.listDrivers()

}
