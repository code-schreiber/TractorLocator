package com.toolslab.tractorlocator.base_network

internal object ApiEndpoint {

    internal object Header {
        const val AUTHORIZATION = "Authorization"
    }

    internal object Query {
        const val EMAIL = "email"
        const val PASSWORD = "password"
    }

    internal object Endpoint {
        const val AUTH_SESSION = "auth/session"

        const val FIELDS = "fields"
        const val MEMBERS = "members"
    }

    const val API_BASE_URL = "https://mytime.trecker.com/api/v3/"

}
