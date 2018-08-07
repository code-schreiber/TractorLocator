package com.toolslab.tractorlocator.view.main

import com.toolslab.tractorlocator.base_mvp.BaseView
import com.toolslab.tractorlocator.base_mvp.MvpPresenter
import com.toolslab.tractorlocator.base_repository.model.DriverViewModel
import com.toolslab.tractorlocator.base_repository.model.FieldViewModel

interface TractorLocatorContract {

    interface Presenter : MvpPresenter<View> {
        fun onMapReady()
    }

    interface View : BaseView {
        fun getMapAsync()

        fun moveCamera(minLatitude: Double, minLongitude: Double, maxLatitude: Double, maxLongitude: Double)

        fun addMapDriver(driverViewModel: DriverViewModel)

        fun addMapField(fieldViewModel: FieldViewModel)

        fun showLoading()

        fun hideLoading()
    }

}
