package com.example.yelpapp.data.repository

import arrow.core.right
import com.example.yelpapp.data.datasource.BusinessLocalDataSource
import com.example.yelpapp.data.datasource.BusinessRemoteDataSource
import com.example.yelpapp.testshared.sampleBusiness
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class BusinessRepositoryTest{

    @Mock
    lateinit var localDataSource: BusinessLocalDataSource

    @Mock
    lateinit var remoteDataSource: BusinessRemoteDataSource

    @Mock
    lateinit var regionRepository: RegionRepository

    private lateinit var businessRepository: BusinessRepository

    private val localBusiness = flowOf(listOf(sampleBusiness.copy(id = "id_test")))

    @Before
    fun setUp(){
        whenever(localDataSource.business).thenReturn(localBusiness)
        businessRepository = BusinessRepository(regionRepository, localDataSource, remoteDataSource)
    }

    @Test
    fun `Business are taken from local if available`() : Unit = runBlocking {
        val result = businessRepository.business
        assertEquals(localBusiness,result)
    }

    @Test
    fun `Business are saved to local when it's empty`() : Unit = runBlocking {
        val remoteBusiness = listOf(sampleBusiness.copy("id_test_remote"))
        whenever(localDataSource.isEmpty()).thenReturn(true)
        whenever(regionRepository.findLastLocation()).thenReturn(RegionRepository.DEFAULT_REGION)
        whenever(remoteDataSource.searchBusiness(any())).thenReturn(remoteBusiness.right())

        businessRepository.requestBusiness()

        verify(localDataSource).save(remoteBusiness)
    }

    @Test
    fun `Finding a business by id`() : Unit = runBlocking {
        val business = flowOf(sampleBusiness.copy("id_test"))
        whenever(localDataSource.findById("id_test")).thenReturn(business)

        val result = businessRepository.getBusinessById("id_test")

        assertEquals(business,result)
    }

    @Test
    fun `Switching favorite updates local`() : Unit = runBlocking {
        val business = sampleBusiness.copy(id = "id_test")

        businessRepository.switchFavorite(business)

        verify(localDataSource).save(argThat { get(0).id == "id_test" })
    }

    @Test
    fun `Switching favorite marks as favorite an unfavorite business`() : Unit = runBlocking {
        val business = sampleBusiness.copy(favorite = false)

        businessRepository.switchFavorite(business)

        verify(localDataSource).save(argThat { get(0).favorite })
    }

    @Test
    fun `Switching favorite marks as unfavorite an favorite business`() : Unit = runBlocking {
        val business = sampleBusiness.copy(favorite = true)

        businessRepository.switchFavorite(business)

        verify(localDataSource).save(argThat { !get(0).favorite })
    }
}