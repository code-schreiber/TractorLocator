package com.toolslab.tractorlocator.base_network.model

import com.squareup.moshi.Json

data class Field(

        @Json(name = "active")
        val active: Boolean,

        @Json(name = "area_in_hectares")
        val areaInHectares: Double,

        @Json(name = "id")
        val id: Int,

        @Json(name = "client_identifier")
        val clientIdentifier: Int?,

        @Json(name = "company")
        val company: Company,

        @Json(name = "sub_client_identifier")
        val subClientIdentifier: Any?,

        @Json(name = "name")
        val name: String,

        @Json(name = "geometric_area")
        val geometricArea: GeometricArea,

        @Json(name = "polyline")
        val polyline: String,

        @Json(name = "centroid_longitude")
        val centroidLongitude: Double,

        @Json(name = "centroid_latitude")
        val centroidLatitude: Double,

        @Json(name = "radius")
        val radius: Double
)
