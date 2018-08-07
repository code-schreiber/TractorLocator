package com.toolslab.tractorlocator.base_network.model

import com.squareup.moshi.Json

data class Address(

        @Json(name = "text")
        val text: String,

        @Json(name = "longitude")
        val longitude: Double,

        @Json(name = "latitude")
        val latitude: Double
)
