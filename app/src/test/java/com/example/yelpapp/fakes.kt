package com.example.yelpapp

import com.example.yelpapp.data.PermissionChecker
import com.example.yelpapp.data.database.BusinessDao
import com.example.yelpapp.data.datasource.LocationDataSource
import com.example.yelpapp.data.server.RemoteResult
import com.example.yelpapp.data.server.RemoteService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.yelpapp.data.database.model.Business as LocalBusiness
import com.example.yelpapp.data.server.model.RemoteBusiness
import com.example.yelpapp.domain.Coordinates

class FakeBusinessDao(business : List<LocalBusiness> = emptyList()) : BusinessDao{

    private val inMemory = MutableStateFlow(business)
    private lateinit var findBusinessFlow : MutableStateFlow<LocalBusiness>

    override fun getAll(): Flow<List<LocalBusiness>> = inMemory

    override fun findById(id: String): Flow<LocalBusiness> {
        findBusinessFlow = MutableStateFlow(inMemory.value.first { it.id == id })
        return findBusinessFlow
    }

    override suspend fun businessCount(): Int = inMemory.value.size

    override suspend fun insertBusiness(business: List<LocalBusiness>) {
        inMemory.value = business

        if(::findBusinessFlow.isInitialized){
            business.firstOrNull() { it.id == findBusinessFlow.value.id }?.let {
                findBusinessFlow.value = it
            }
        }
    }
}

class FakeRemoteService(private val business : List<RemoteBusiness> = emptyList()) : RemoteService{
    override suspend fun searchBusinesses(latitude: Double, longitude: Double) = RemoteResult(
        business,
        business.size
    )
}

class FakeLocationDataSource : LocationDataSource{
    var coordinates = Coordinates(15.000,45.000)

    override suspend fun findLastLocation(): Coordinates = coordinates
}

class FakePermissionChecker : PermissionChecker{
    var permissionGranted = true

    override fun check(permission: PermissionChecker.Permission): Boolean = permissionGranted
}