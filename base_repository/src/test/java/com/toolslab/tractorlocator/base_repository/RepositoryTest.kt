package com.toolslab.tractorlocator.base_repository

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.toolslab.tractorlocator.base_network.model.Field
import com.toolslab.tractorlocator.base_network.model.LastKnownPosition
import com.toolslab.tractorlocator.base_network.model.Member
import com.toolslab.tractorlocator.base_repository.model.DriverViewModel
import com.toolslab.tractorlocator.base_repository.model.FieldViewModel
import io.reactivex.Single
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class RepositoryTest {

    private val mockFieldViewModel1: FieldViewModel = mock()
    private val mockFieldViewModel2: FieldViewModel = mock()
    private val mockField1: Field = mock()
    private val mockField2: Field = mock()

    private val mockDriverViewModel1: DriverViewModel = mock()
    private val mockMember1: Member = mock()
    private val mockMember2: Member = mock()

    private val underTest = Repository()

    @Before
    fun setUp() {
        underTest.apiService = mock()
        underTest.errorHandler = mock()
        underTest.fieldModelConverter = mock()
        underTest.memberModelConverter = mock()
    }

    @Test
    fun listFields() {
        whenever(underTest.apiService.listFields()).thenReturn(Single.just(listOf(mockField1, mockField2)))
        whenever(underTest.fieldModelConverter.convert(mockField1)).thenReturn(mockFieldViewModel1)
        whenever(underTest.fieldModelConverter.convert(mockField2)).thenReturn(mockFieldViewModel2)

        val result = underTest.listFields().blockingGet()

        result.size shouldEqual 2
        result[0] shouldEqual mockFieldViewModel1
        result[1] shouldEqual mockFieldViewModel2
        verifyZeroInteractions(underTest.errorHandler)
    }

    @Test
    fun listFieldsWithError() {
        val exception = Exception("an exception")
        val handledException = Exception("a handled exception")
        whenever(underTest.apiService.listFields()).thenReturn(Single.error(exception))
        whenever(underTest.errorHandler.handle<Field>(exception)).thenReturn(Single.error(handledException))

        val testObserver = underTest.listFields().test()
        testObserver.awaitTerminalEvent()

        testObserver.apply {
            valueCount() shouldEqual 0
            errorCount() shouldEqual 1
            errors()[0] shouldEqual handledException
        }
        verifyZeroInteractions(underTest.fieldModelConverter)
    }

    @Test
    fun listMembers() {
        whenever(underTest.apiService.listMembers()).thenReturn(Single.just(listOf(mockMember1, mockMember2)))
        whenever(underTest.memberModelConverter.convert(mockMember1)).thenReturn(mockDriverViewModel1)
        whenever(mockMember1.lastKnownPosition).thenReturn(LastKnownPosition(1.1, 2.2, ""))
        whenever(mockMember2.lastKnownPosition).thenReturn(LastKnownPosition(null, 3.3, ""))

        val result = underTest.listDrivers().blockingGet()

        result.size shouldEqual 1
        result[0] shouldEqual mockDriverViewModel1
        verifyZeroInteractions(underTest.errorHandler)
    }

    @Test
    fun listDriversWithError() {
        val exception = Exception("an exception")
        val handledException = Exception("a handled exception")
        whenever(underTest.apiService.listMembers()).thenReturn(Single.error(exception))
        whenever(underTest.errorHandler.handle<Member>(exception)).thenReturn(Single.error(handledException))

        val testObserver = underTest.listDrivers().test()
        testObserver.awaitTerminalEvent()

        testObserver.apply {
            valueCount() shouldEqual 0
            errorCount() shouldEqual 1
            errors()[0] shouldEqual handledException
        }
        verifyZeroInteractions(underTest.memberModelConverter)
    }

}
