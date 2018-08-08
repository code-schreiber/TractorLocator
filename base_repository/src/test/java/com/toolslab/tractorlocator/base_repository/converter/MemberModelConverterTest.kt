package com.toolslab.tractorlocator.base_repository.converter

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.toolslab.tractorlocator.base_network.model.LastKnownPosition
import com.toolslab.tractorlocator.base_network.model.Member
import org.amshove.kluent.shouldEqual
import org.junit.Test

class MemberModelConverterTest {

    private val name = "first name"
    private val time = "a time"
    private val longitude = 2.2
    private val latitude = 1.1

    private val member: Member = mock()

    private val underTest = MemberModelConverter()

    @Test
    fun convert() {
        whenever(member.firstName).thenReturn(name)
        whenever(member.lastKnownPosition).thenReturn(LastKnownPosition(longitude, latitude, time))

        val driverViewModel = underTest.convert(member)

        driverViewModel.name shouldEqual name
        driverViewModel.lastSeenDate shouldEqual time
        driverViewModel.coordinate.latitude shouldEqual latitude
        driverViewModel.coordinate.longitude shouldEqual longitude
    }

}
