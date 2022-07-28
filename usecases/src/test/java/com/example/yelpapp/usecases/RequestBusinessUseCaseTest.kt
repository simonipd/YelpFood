package com.example.yelpapp.usecases

import com.example.yelpapp.data.repository.BusinessRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class RequestBusinessUseCaseTest{

    @Test
    fun `Invoke calls business repository`(): Unit = runBlocking {
        val businessRepository = mock<BusinessRepository>()
        val requestPopularMoviesUseCase = RequestBusinessUseCase(businessRepository)

        requestPopularMoviesUseCase()

        verify(businessRepository).requestBusiness()
    }
}