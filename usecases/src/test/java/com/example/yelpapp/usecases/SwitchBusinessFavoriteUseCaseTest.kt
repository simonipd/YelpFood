package com.example.yelpapp.usecases

import com.example.yelpapp.data.repository.BusinessRepository
import com.example.yelpapp.testshared.sampleBusiness
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SwitchBusinessFavoriteUseCaseTest{
    @Test
    fun `Invoke calls business repository`(): Unit = runBlocking {
        val business = sampleBusiness.copy(id = "id_test")
        val businessRepository = mock<BusinessRepository>()
        val switchBusinessFavoriteUseCase = SwitchBusinessFavoriteUseCase(businessRepository)

        switchBusinessFavoriteUseCase(business)

        verify(businessRepository).switchFavorite(business)
    }
}