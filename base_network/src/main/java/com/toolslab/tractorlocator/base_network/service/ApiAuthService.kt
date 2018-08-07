package com.toolslab.tractorlocator.base_network.service

import com.toolslab.tractorlocator.base_network.ApiEndpoint.Endpoint.AUTH_SESSION
import com.toolslab.tractorlocator.base_network.ApiEndpoint.Query.EMAIL
import com.toolslab.tractorlocator.base_network.ApiEndpoint.Query.PASSWORD
import com.toolslab.tractorlocator.base_network.model.Jwt
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiAuthService {

    @FormUrlEncoded
    @POST(AUTH_SESSION)
    fun getJwt(
            @Field(EMAIL) email: String,
            @Field(PASSWORD) password: String
    ): Single<Jwt>

}
