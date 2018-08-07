package com.toolslab.tractorlocator.base_network.model

import com.squareup.moshi.Json

data class Member(

        @Json(name = "active")
        val active: Boolean,

        @Json(name = "app_version")
        val appVersion: Any?,

        @Json(name = "client_identifier")
        val clientIdentifier: Int?,

        @Json(name = "company_id")
        val companyId: Int,

        @Json(name = "current_member")
        val currentMember: Boolean,

        @Json(name = "device_name")
        val deviceName: Any?,

        @Json(name = "driver")
        val driver: Boolean,

        @Json(name = "email")
        val email: String,

        @Json(name = "first_name")
        val firstName: String?,

        @Json(name = "full_name")
        val fullName: String?,

        @Json(name = "hourly_rate")
        val hourlyRate: String?,

        @Json(name = "id")
        val id: Int,

        @Json(name = "last_name")
        val lastName: String?,

        @Json(name = "tenant_id")
        val tenantId: Int,

        @Json(name = "tenant_status")
        val tenantStatus: String,

        @Json(name = "tenant_active")
        val tenantActive: Boolean,

        @Json(name = "terms_accepted")
        val termsAccepted: Boolean,

        @Json(name = "order_document")
        val orderDocument: Any?,

        @Json(name = "order_confirmed")
        val orderConfirmed: Boolean,

        @Json(name = "working_since")
        val workingSince: Any?,

        @Json(name = "last_known_position")
        val lastKnownPosition: LastKnownPosition
)
