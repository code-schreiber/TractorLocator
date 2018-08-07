package com.toolslab.tractorlocator.base_network.storage

import android.support.annotation.VisibleForTesting
import javax.inject.Inject

class CredentialsStorage @Inject constructor() {

    @VisibleForTesting
    internal val cachedToken = Token("")

    internal fun getToken() = cachedToken.token

    internal fun saveToken(token: String) {
        cachedToken.token = token
    }

    internal fun getCredentials() = Credentials("", "")

}
