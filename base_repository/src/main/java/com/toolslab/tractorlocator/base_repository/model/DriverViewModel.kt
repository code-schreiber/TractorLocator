package com.toolslab.tractorlocator.base_repository.model

data class DriverViewModel(
        val name: String,
        val lastSeenDate: String,
        val coordinate: CoordinateViewModel
)
