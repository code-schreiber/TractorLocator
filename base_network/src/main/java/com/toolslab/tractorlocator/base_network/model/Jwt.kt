package com.toolslab.tractorlocator.base_network.model

import com.squareup.moshi.Json

data class Jwt(

        @Json(name = "token")
        val token: String,

        @Json(name = "working_since")
        val workingSince: Any?,

        @Json(name = "current_task_id")
        val currentTaskId: Any?,

        @Json(name = "tenant_state")
        val tenantState: String,

        @Json(name = "tenant_active")
        val tenantActive: Boolean

)
