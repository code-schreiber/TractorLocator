package com.toolslab.tractorlocator.base_network

import com.toolslab.tractorlocator.base_network.ApiEndpoint.Header.AUTHORIZATION
import com.toolslab.tractorlocator.base_network.service.ApiAuthService
import com.toolslab.tractorlocator.base_network.storage.CredentialsStorage
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class Reauthenticator @Inject constructor() : Authenticator {

    @Inject
    internal lateinit var credentialsStorage: CredentialsStorage

    @Inject
    internal lateinit var apiAuthService: ApiAuthService

    override fun authenticate(route: Route?, response: Response?): Request? {
        if (response == null) return null
        val originalRequest = response.request()
        if (originalRequest.header(AUTHORIZATION) != null) return null // Already failed to authenticate
        if (credentialsStorage.getToken().isEmpty()) {
            synchronized(this) {
                if (credentialsStorage.getToken().isEmpty()) { // Check if another thread already got a token
                    // Get and save token first
                    val credentials = credentialsStorage.getCredentials()
                    apiAuthService.getJwt(credentials.email, credentials.password)
                            .map { credentialsStorage.saveToken(it.token) }
                            .blockingGet()
                }
            }
        }
        return originalRequest.newBuilder()
                .header(AUTHORIZATION, credentialsStorage.getToken())
                .build()
    }

}
