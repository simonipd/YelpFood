package com.example.yelpapp

import com.example.yelpapp.data.database.BusinessRoomDataSource
import com.example.yelpapp.data.datasource.BusinessLocalDataSource
import com.example.yelpapp.data.datasource.BusinessRemoteDataSource
import com.example.yelpapp.data.repository.BusinessRepository
import com.example.yelpapp.data.repository.RegionRepository
import com.example.yelpapp.data.server.BusinessServerDataSource
import com.example.yelpapp.data.database.model.Business as LocalBusiness
import com.example.yelpapp.data.server.model.RemoteBusiness

fun buildRepositoryWith(
    localData : List<LocalBusiness>,
    remoteData : List<RemoteBusiness>
) : BusinessRepository{
    val locationDataSource = FakeLocationDataSource()
    val permissionChecker = FakePermissionChecker()
    val regionRepository = RegionRepository(locationDataSource,permissionChecker)
    val localDataSource = BusinessRoomDataSource(FakeBusinessDao(localData))
    val remoteDataSource = BusinessServerDataSource(FakeRemoteService(remoteData))

    return BusinessRepository(regionRepository, localDataSource, remoteDataSource)
}

fun buildLocalBusiness(vararg id : String) = id.map {
    LocalBusiness(
        id = it,
        alias = "alias $it",
        display_phone = "display phone",
        distance = 3.0,
        image_url = "image",
        is_closed = false,
        address = "address",
        city = "city",
        country = "country",
        name = "name",
        phone = "phone",
        price = "$$",
        rating =  5.0,
        review_count = 40,
        url = "url",
        favorite = false
    )
}

fun buildRemoteBusiness(vararg id : String) = id.map {
    RemoteBusiness(
        alias = "alias $it",
        coordinates = null,
        display_phone = "display phone",
        distance = 3.0,
        id = it,
        image_url = "img",
        is_closed = false,
        location = null,
        name = "name",
        phone = "phone",
        price = "$$",
        rating = 5.0,
        review_count = 5,
        url = "url"
    )
}