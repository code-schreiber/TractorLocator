package com.toolslab.tractorlocator.base_network.service

import com.toolslab.tractorlocator.base_network.ApiEndpoint.Endpoint.FIELDS
import com.toolslab.tractorlocator.base_network.ApiEndpoint.Endpoint.MEMBERS
import com.toolslab.tractorlocator.base_network.model.Field
import com.toolslab.tractorlocator.base_network.model.Member
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET(FIELDS)
    fun listFields(): Single<List<Field>>

    @GET(MEMBERS)
    fun listMembers(): Single<List<Member>>

}
