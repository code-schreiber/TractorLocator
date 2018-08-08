package com.toolslab.tractorlocator.maps

import com.nhaarman.mockito_kotlin.mock
import com.toolslab.tractorlocator.base_repository.model.CoordinateViewModel
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.any
import org.mockito.Mockito.verify

class MapOperationsTest {

    private val underTest = MapOperations()

    @Before
    fun setUp() {
        underTest.googleMap = mock()
        underTest.resourceOperations = mock()
    }

    @Test
    fun addField() {
        underTest.addField(listOf(CoordinateViewModel(1.1, 2.2)))

        verify(underTest.googleMap).addPolygon(any())
        verify(underTest.resourceOperations).color(underTest.fieldColor)
    }

    @Test
    fun extractRelativeTimeSpan() {
        underTest.extractRelativeTimeSpan("") shouldEqual ""
    }

}
