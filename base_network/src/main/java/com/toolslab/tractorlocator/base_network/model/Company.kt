package com.toolslab.tractorlocator.base_network.model

import com.squareup.moshi.Json

data class Company(

        @Json(name = "id")
        val id: Int,

        @Json(name = "name")
        val name: String,

        @Json(name = "address")
        val address: Address,

        @Json(name = "default")
        val default: Boolean
)
