package com.toolslab.tractorlocator.base_network

import com.nhaarman.mockito_kotlin.*
import com.toolslab.tractorlocator.base_network.ApiEndpoint.Header.AUTHORIZATION
import com.toolslab.tractorlocator.base_network.model.Jwt
import com.toolslab.tractorlocator.base_network.storage.Credentials
import io.reactivex.Single
import okhttp3.Request
import okhttp3.Response
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class ReauthenticatorTest {

    private val email = "email"
    private val password = "password"
    private val jwt = Jwt("a token", "", "", "", false)

    private val mockRequest: Request = mock()
    private val mockNewRequest: Request = mock()
    private val mockResponse: Response = mock()
    private val mockBuilder: Request.Builder = mock()

    private val underTest = Reauthenticator()

    @Before
    fun setUp() {
        underTest.credentialsStorage = mock()
        underTest.apiAuthService = mock()
    }

    @Test
    fun authenticateWithNull() {
        val request = underTest.authenticate(null, null)

        request shouldEqual null
        verifyZeroInteractions(underTest.credentialsStorage)
        verifyZeroInteractions(underTest.apiAuthService)
    }

    @Test
    fun authenticateAlreadyFailedToAuthenticate() {
        whenever(mockResponse.request()).thenReturn(mockRequest)
        whenever(mockRequest.header(ApiEndpoint.Header.AUTHORIZATION)).thenReturn("a header's token")

        val response = underTest.authenticate(null, mockResponse)

        response shouldEqual null
        verifyZeroInteractions(underTest.credentialsStorage)
        verifyZeroInteractions(underTest.apiAuthService)
    }

    @Test
    fun authenticateWhenTokenExists() {
        whenever(mockResponse.request()).thenReturn(mockRequest)
        whenever(mockRequest.header(ApiEndpoint.Header.AUTHORIZATION)).thenReturn(null)
        whenever(mockRequest.newBuilder()).thenReturn(mockBuilder)
        whenever(mockBuilder.header(AUTHORIZATION, jwt.token)).thenReturn(mockBuilder)
        whenever(mockBuilder.build()).thenReturn(mockNewRequest)
        whenever(underTest.credentialsStorage.getToken()).thenReturn(jwt.token)

        val request = underTest.authenticate(null, mockResponse)

        request shouldEqual mockNewRequest
        verify(underTest.credentialsStorage, times(2)).getToken()
        verifyNoMoreInteractions(underTest.credentialsStorage)
        verifyZeroInteractions(underTest.apiAuthService)
    }

    @Test
    fun authenticateWhenTokenIsMissing() {
        val token = ""
        whenever(mockResponse.request()).thenReturn(mockRequest)
        whenever(mockRequest.header(ApiEndpoint.Header.AUTHORIZATION)).thenReturn(null)
        whenever(mockRequest.newBuilder()).thenReturn(mockBuilder)
        whenever(mockBuilder.header(AUTHORIZATION, token)).thenReturn(mockBuilder)
        whenever(mockBuilder.build()).thenReturn(mockNewRequest)
        whenever(underTest.credentialsStorage.getToken()).thenReturn(token)
        whenever(underTest.credentialsStorage.getCredentials()).thenReturn(Credentials(email, password))
        whenever(underTest.apiAuthService.getJwt(email, password)).thenReturn(Single.just(jwt))

        val request = underTest.authenticate(null, mockResponse)

        request shouldEqual mockNewRequest
        verify(underTest.credentialsStorage, times(3)).getToken()
        verify(underTest.credentialsStorage).getCredentials()
        verify(underTest.credentialsStorage).saveToken(jwt.token)
        verify(underTest.apiAuthService).getJwt(email, password)
        verifyNoMoreInteractions(underTest.credentialsStorage)
        verifyNoMoreInteractions(underTest.apiAuthService)
    }

}
