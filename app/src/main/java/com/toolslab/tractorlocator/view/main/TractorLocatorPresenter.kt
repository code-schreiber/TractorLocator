package com.toolslab.tractorlocator.view.main

import android.support.annotation.VisibleForTesting
import com.toolslab.tractorlocator.base_mvp.BasePresenter
import com.toolslab.tractorlocator.base_repository.exception.NoConnectionException
import com.toolslab.tractorlocator.base_repository.model.CoordinateViewModel
import com.toolslab.tractorlocator.base_repository.model.DriverViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class TractorLocatorPresenter @Inject constructor() :
        BasePresenter<TractorLocatorContract.View>(), TractorLocatorContract.Presenter {

    @Inject
    internal lateinit var compositeDisposable: CompositeDisposable

    @Inject
    internal lateinit var tractorLocatorInteractor: TractorLocatorInteractor

    override fun onBound(view: TractorLocatorContract.View) {
        view.showLoading()
        view.getMapAsync()
    }

    override fun onUnbound(view: TractorLocatorContract.View) {
        compositeDisposable.clear()
    }

    override fun onMapReady() {
        listFields()
        listDrivers()
    }

    private fun listFields() {
        compositeDisposable.add(tractorLocatorInteractor.listFields()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Timber.d("${it.size} fields found")
                            it.forEach {
                                view.addMapField(it)
                            }
                            view.hideLoading()
                        },
                        {
                            Timber.e(it)
                            view.hideLoading()
                            when (it) {
                                is NoConnectionException -> view.showNoConnectionError()
                                else -> view.showError(it.message)
                            }
                        }
                )
        )
    }

    private fun listDrivers() {
        compositeDisposable.add(tractorLocatorInteractor.listDrivers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Timber.d("${it.size} drivers found")
                            zoomIn(it)
                            it.forEach {
                                view.addMapDriver(it)
                            }
                            view.hideLoading()
                        },
                        {
                            Timber.e(it)
                            view.hideLoading()
                            when (it) {
                                is NoConnectionException -> view.showNoConnectionError()
                                else -> view.showError(it.message)
                            }
                        }
                )
        )
    }

    @VisibleForTesting
    internal fun zoomIn(drivers: List<DriverViewModel>) {
        // Zoom into map close enough to show all drivers
        val latitudes = drivers.map { it.coordinate.latitude }
        val longitudes = drivers.map { it.coordinate.longitude }
        view.moveCamera(CoordinateViewModel(latitudes.median(), longitudes.median()))
    }

}

@VisibleForTesting
internal fun List<Double>.median(): Double {
    val sorted = this.sorted()
    val middleIndex = sorted.size / 2
    val middle = sorted[middleIndex]
    return if (sorted.size % 2 == 1) {
        middle
    } else {
        val oneBeforeMiddle = sorted[middleIndex - 1]
        (oneBeforeMiddle + middle) / 2.0
    }
}
