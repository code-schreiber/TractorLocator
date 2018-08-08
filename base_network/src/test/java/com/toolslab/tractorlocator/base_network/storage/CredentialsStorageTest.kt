package com.toolslab.tractorlocator.base_network.storage

import org.amshove.kluent.shouldEqual
import org.junit.Test

class CredentialsStorageTest {

    private val tokenString = "tokenString"

    private var underTest = CredentialsStorage()

    @Test
    fun getToken() {
        underTest.cachedToken.token = tokenString

        val token = underTest.getToken()

        token shouldEqual tokenString
    }

    @Test
    fun saveToken() {
        val someToken = "some token"

        underTest.saveToken(someToken)

        underTest.getToken() shouldEqual someToken
    }


    @Test
    fun getCredentials() {
        val credentials = underTest.getCredentials()

        credentials shouldEqual Credentials("", "")
    }

}
