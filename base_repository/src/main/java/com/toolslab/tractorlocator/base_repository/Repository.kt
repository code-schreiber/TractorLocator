package com.toolslab.tractorlocator.base_repository

import com.toolslab.tractorlocator.base_network.service.ApiService
import com.toolslab.tractorlocator.base_repository.converter.FieldModelConverter
import com.toolslab.tractorlocator.base_repository.converter.MemberModelConverter
import com.toolslab.tractorlocator.base_repository.model.DriverViewModel
import com.toolslab.tractorlocator.base_repository.model.FieldViewModel
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor() {

    @Inject
    internal lateinit var apiService: ApiService

    @Inject
    internal lateinit var errorHandler: ErrorHandler

    @Inject
    internal lateinit var fieldModelConverter: FieldModelConverter

    @Inject
    internal lateinit var memberModelConverter: MemberModelConverter

    fun listFields(): Single<List<FieldViewModel>> =
            apiService.listFields()
                    .onErrorResumeNext {
                        errorHandler.handle(it)
                    }
                    .flattenAsObservable { it }
                    .map {
                        fieldModelConverter.convert(it)
                    }
                    .toList()

    fun listDrivers(): Single<List<DriverViewModel>> =
            apiService.listMembers()
                    .onErrorResumeNext {
                        errorHandler.handle(it)
                    }
                    .flattenAsObservable { it }
                    .filter {
                        // We can only show members with a last known position
                        it.lastKnownPosition.run { latitude != null && longitude != null }
                    }
                    .map {
                        memberModelConverter.convert(it)
                    }
                    .toList()

}
