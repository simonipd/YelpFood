package com.example.yelpapp.data.repository


import com.example.yelpapp.data.datasource.BusinessLocalDataSource
import com.example.yelpapp.data.datasource.BusinessRemoteDataSource
import com.example.yelpapp.domain.Business
import com.example.yelpapp.domain.Error
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BusinessRepository @Inject constructor(
    private val regionRepository : RegionRepository,
    private val localDataSource : BusinessLocalDataSource,
    private val remoteDataSource : BusinessRemoteDataSource
) {
    val business get() = localDataSource.business

    fun getBusinessById(id: String): Flow<Business> = localDataSource.findById(id)

    suspend fun requestBusiness(): Error? {
        if(localDataSource.isEmpty()){
            val business = remoteDataSource.searchBusiness(regionRepository.findLastLocation())
            business.fold(ifLeft = { return it }){
                localDataSource.save(it)
            }
        }
        return null
    }

    suspend fun switchFavorite(business: Business): Error? {
        val updatedBusiness = business.copy(favorite = !business.favorite)
        return localDataSource.save(listOf(updatedBusiness))
    }
}