package com.example.yelpapp.usecases

import com.example.yelpapp.testshared.sampleBusiness
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetBusinessByIdUseCaseTest{

    @Test
    fun `Invoke calls business repository`(): Unit = runBlocking {
        val business = flowOf(sampleBusiness.copy(id = "id_test"))
        val getBusinessByIdUseCase = GetBusinessByIdUseCase(mock() {
            on { getBusinessById("id_test") } doReturn (business)
        })

        val result = getBusinessByIdUseCase("id_test")

        assertEquals(business, result)
    }
}