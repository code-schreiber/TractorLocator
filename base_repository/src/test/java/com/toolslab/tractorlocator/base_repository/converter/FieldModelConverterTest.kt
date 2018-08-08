package com.toolslab.tractorlocator.base_repository.converter

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.toolslab.tractorlocator.base_network.model.Field
import com.toolslab.tractorlocator.base_network.model.GeometricArea
import org.amshove.kluent.shouldEqual
import org.junit.Test

class FieldModelConverterTest {

    private val field: Field = mock()

    private val underTest = FieldModelConverter()

    @Test
    fun convert() {
        val coordinates1 = listOf(1.1, 2.2)
        val coordinates2 = listOf(3.3, 4.4)
        whenever(field.geometricArea).thenReturn(GeometricArea("", listOf(listOf(coordinates1, coordinates2))))

        val result = underTest.convert(field)

        result.coordinates.size shouldEqual 2
        result.coordinates[0].latitude shouldEqual coordinates1[1]
        result.coordinates[0].longitude shouldEqual coordinates1[0]
        result.coordinates[1].latitude shouldEqual coordinates2[1]
        result.coordinates[1].longitude shouldEqual coordinates2[0]
    }

}
