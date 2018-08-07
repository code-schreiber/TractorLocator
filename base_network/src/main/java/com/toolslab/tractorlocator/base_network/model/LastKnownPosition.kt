package com.toolslab.tractorlocator.base_network.model

import com.squareup.moshi.Json

data class LastKnownPosition(

        @Json(name = "longitude")
        val longitude: Double?,

        @Json(name = "latitude")
        val latitude: Double?,

        @Json(name = "timestamp")
        val timestamp: String?
)
