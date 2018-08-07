package com.toolslab.tractorlocator.base_network.model

import com.squareup.moshi.Json

data class GeometricArea(

        @Json(name = "type")
        val type: String,

        @Json(name = "coordinates")
        val coordinates: List<List<List<Double>>>
)
