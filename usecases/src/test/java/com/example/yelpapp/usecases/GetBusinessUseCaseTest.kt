package com.example.yelpapp.usecases

import com.example.yelpapp.testshared.sampleBusiness
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetBusinessUseCaseTest {

    @Test
    fun `Invoke calls business repository`(): Unit = runBlocking {
        val businesses = flowOf(listOf(sampleBusiness.copy(id = "id_test")))
        val getPopularMoviesUseCase = GetBusinessUseCase(mock {
            on { business } doReturn businesses
        })

        val result = getPopularMoviesUseCase()

        assertEquals(businesses, result)
    }
}