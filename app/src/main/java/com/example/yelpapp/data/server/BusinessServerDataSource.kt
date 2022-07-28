package com.example.yelpapp.data.server

import arrow.core.Either
import com.example.yelpapp.data.datasource.BusinessRemoteDataSource
import com.example.yelpapp.data.server.model.toDomainModel
import com.example.yelpapp.data.tryCall
import com.example.yelpapp.domain.Business
import com.example.yelpapp.domain.Coordinates
import com.example.yelpapp.domain.Error
import javax.inject.Inject


class BusinessServerDataSource @Inject constructor(
    private val service : RemoteService
    ) : BusinessRemoteDataSource {

    override suspend fun searchBusiness(coordinates: Coordinates): Either<Error, List<Business>> = tryCall {
        service
            .searchBusinesses(coordinates.latitude, coordinates.longitude)
            .businesses.map { it.toDomainModel() }
    }
}