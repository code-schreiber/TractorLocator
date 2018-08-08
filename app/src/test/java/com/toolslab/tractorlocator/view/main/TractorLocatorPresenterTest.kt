package com.toolslab.tractorlocator.view.main

import com.nhaarman.mockito_kotlin.*
import com.toolslab.tractorlocator.base_repository.exception.NoConnectionException
import com.toolslab.tractorlocator.base_repository.model.CoordinateViewModel
import com.toolslab.tractorlocator.base_repository.model.DriverViewModel
import com.toolslab.tractorlocator.base_repository.model.FieldViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class TractorLocatorPresenterTest {

    private val driver1 = DriverViewModel("driver1", "snippet1", CoordinateViewModel(-10.10, -20.20))
    private val driver2 = DriverViewModel("driver2", "snippet2", CoordinateViewModel(30.30, 40.40))
    private val drivers = listOf(driver1, driver2)
    private val field1 = FieldViewModel(listOf(CoordinateViewModel(-10.10, -20.20)))
    private val field2 = FieldViewModel(listOf(CoordinateViewModel(30.30, 40.40)))
    private val fields = listOf(field1, field2)
    private val driversMedianCoordinates = CoordinateViewModel(10.100000000000001, 10.1)

    private val mockView: TractorLocatorContract.View = mock()

    private val underTest = TractorLocatorPresenter()

    companion object {

        @BeforeClass
        @JvmStatic
        fun setUpRxSchedulers() {
            val immediate = object : Scheduler() {

                // this prevents StackOverflowErrors when scheduling with a delay
                override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit) = super.scheduleDirect(run, 0, unit)

                override fun createWorker() = ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }

            RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
            RxJavaPlugins.setIoSchedulerHandler { immediate }
        }
    }

    @Before
    fun setUp() {
        underTest.compositeDisposable = mock()
        underTest.tractorLocatorInteractor = mock()
        underTest.bind(mockView)
    }

    @Test
    fun onBound() {
        verify(mockView).showLoading()
        verify(mockView).getMapAsync()
    }

    @Test
    fun onUnbound() {
        underTest.onUnbound(mockView)

        verify(underTest.compositeDisposable).clear()
    }

    @Test
    fun onMapReady() {
        whenever(underTest.tractorLocatorInteractor.listFields()).thenReturn(Single.just(fields))
        whenever(underTest.tractorLocatorInteractor.listDrivers()).thenReturn(Single.just(drivers))

        underTest.onMapReady()

        verify(mockView).addMapField(field1)
        verify(mockView).addMapField(field2)
        verify(mockView).addMapDriver(driver1)
        verify(mockView).addMapDriver(driver2)
        verify(mockView).moveCamera(driversMedianCoordinates)
        verify(mockView, times(2)).hideLoading()
        verify(underTest.compositeDisposable, times(2)).add(any())
    }

    @Test
    fun onMapReadyWithError() {
        val error = "error message"
        whenever(underTest.tractorLocatorInteractor.listFields()).thenReturn(Single.error(Exception(error)))
        whenever(underTest.tractorLocatorInteractor.listDrivers()).thenReturn(Single.error(NoConnectionException(Exception())))

        underTest.onMapReady()

        verify(mockView, times(2)).hideLoading()
        verify(mockView).showError(error)
        verify(mockView).showNoConnectionError()
    }

    @Test
    fun zoomIn() {
        underTest.zoomIn(drivers)

        verify(mockView).moveCamera(driversMedianCoordinates)
    }

    @Test
    fun median() {
        listOf(1.0, 2.0, 1000.0).median() shouldEqual 2.0
        listOf(1.0, 2.0, 3.0, 1000.0).median() shouldEqual 2.5
    }

}
