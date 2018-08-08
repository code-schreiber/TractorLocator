package com.toolslab.tractorlocator.view.main

import android.view.View.GONE
import android.view.View.VISIBLE
import com.google.android.gms.maps.GoogleMap
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.toolslab.tractorlocator.base_repository.model.CoordinateViewModel
import com.toolslab.tractorlocator.base_repository.model.DriverViewModel
import com.toolslab.tractorlocator.base_repository.model.FieldViewModel
import org.junit.Before
import org.junit.Test

class TractorLocatorActivityTest {

    private val driver1 = DriverViewModel("driver1", "snippet1", CoordinateViewModel(-10.10, -20.20))
    private val field1 = FieldViewModel(listOf(CoordinateViewModel(-10.10, -20.20)))

    private val underTest = TractorLocatorActivity()

    @Before
    fun setUp() {
        underTest.loadingView = mock()
        underTest.mapOperations = mock()
        underTest.presenter = mock()
    }

    @Test
    fun onMapReady() {
        val googleMap: GoogleMap = mock()

        underTest.onMapReady(googleMap)

        verify(underTest.mapOperations).googleMap = googleMap
        verify(underTest.presenter).onMapReady()
    }

    @Test
    fun addMapField() {
        underTest.addMapField(field1)

        verify(underTest.mapOperations).addField(field1.coordinates)
    }

    @Test
    fun addMapDriver() {
        underTest.addMapDriver(driver1)

        verify(underTest.mapOperations).addMarker(driver1.name, driver1.lastSeenDate, driver1.coordinate)
    }

    @Test
    fun moveCamera() {
        underTest.moveCamera(driver1.coordinate)

        verify(underTest.mapOperations).moveCamera(driver1.coordinate)
    }

    @Test
    fun showLoading() {
        underTest.showLoading()

        verify(underTest.loadingView).visibility = VISIBLE
    }

    @Test
    fun hideLoading() {
        underTest.hideLoading()

        verify(underTest.loadingView).visibility = GONE
    }

}
