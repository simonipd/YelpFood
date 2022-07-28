package com.example.yelpapp.data.database


import com.example.yelpapp.data.database.model.fromDomainModel
import com.example.yelpapp.data.database.model.toDomainModel
import com.example.yelpapp.data.datasource.BusinessLocalDataSource
import com.example.yelpapp.data.tryCall
import kotlinx.coroutines.flow.Flow
import com.example.yelpapp.domain.Business
import com.example.yelpapp.domain.Error
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class BusinessRoomDataSource @Inject constructor(
    private val businessDao: BusinessDao
): BusinessLocalDataSource {

    override val business: Flow<List<Business>> =
        businessDao.getAll().map { it.map { it.toDomainModel() } }

    override suspend fun isEmpty(): Boolean = businessDao.businessCount() == 0

    override fun findById(id: String): Flow<Business> =
        businessDao.findById(id).map { it.toDomainModel() }


    override suspend fun save(business: List<Business>): Error? = tryCall {
        businessDao.insertBusiness(business.map { it.fromDomainModel() })
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )
}