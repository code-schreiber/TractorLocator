package com.toolslab.tractorlocator.base_repository.converter

import com.toolslab.tractorlocator.base_network.model.Field
import com.toolslab.tractorlocator.base_repository.model.CoordinateViewModel
import com.toolslab.tractorlocator.base_repository.model.FieldViewModel
import javax.inject.Inject

class FieldModelConverter @Inject constructor() : Converter<Field, FieldViewModel> {

    override fun convert(source: Field): FieldViewModel {
        val coordinates = source.geometricArea.coordinates[0].map {
            // Latitude and longitude are in the opposite order
            // in the list, so we swap them here
            CoordinateViewModel(it[1], it[0])
        }
        return FieldViewModel(coordinates)
    }
}
