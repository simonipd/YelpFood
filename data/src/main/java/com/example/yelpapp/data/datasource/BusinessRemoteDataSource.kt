package com.example.yelpapp.data.datasource


import arrow.core.Either
import com.example.yelpapp.domain.Business
import com.example.yelpapp.domain.Coordinates
import com.example.yelpapp.domain.Error

interface BusinessRemoteDataSource{
    suspend fun searchBusiness(coordinates: Coordinates) : Either<Error, List<Business>>
}