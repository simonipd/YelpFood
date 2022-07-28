package com.example.yelpapp.ui.detail

import app.cash.turbine.test
import com.example.yelpapp.buildLocalBusiness
import com.example.yelpapp.buildRepositoryWith
import com.example.yelpapp.data.server.model.RemoteBusiness
import com.example.yelpapp.testrules.CoroutinesTestRule
import com.example.yelpapp.ui.detail.DetailViewModel.UiState
import com.example.yelpapp.usecases.GetBusinessByIdUseCase
import com.example.yelpapp.usecases.SwitchBusinessFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

import com.example.yelpapp.data.database.model.Business as LocalBusiness

@ExperimentalCoroutinesApi
class DetailIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `Ui is updated with the business on start`() = runTest {
        val vm = buildViewModelWith(
            id = "3",
            localData = buildLocalBusiness("1","2","3")
        )

        vm.state.test {
            assertEquals(UiState(),awaitItem())
            assertEquals("3",awaitItem().business?.id)
            cancel()
        }
    }

    @Test
    fun `Favorite is update in local`() = runTest {
        val vm = buildViewModelWith(
            id = "1",
            localData = buildLocalBusiness("1","2","3")
        )

        vm.onFavoriteClicked()

        vm.state.test {
            assertEquals(UiState(),awaitItem())
            assertEquals(false,awaitItem().business?.favorite)
            assertEquals(true,awaitItem().business?.favorite)
            cancel()
        }
    }

    private fun buildViewModelWith(
        id : String,
        localData : List<LocalBusiness> = emptyList(),
        remoteData : List<RemoteBusiness> = emptyList()
    ) : DetailViewModel {

        val businessRepository = buildRepositoryWith(localData, remoteData)
        val getBusinessByIdUseCase = GetBusinessByIdUseCase(businessRepository)
        val switchBusinessFavoriteUseCase = SwitchBusinessFavoriteUseCase(businessRepository)
        return DetailViewModel(id,getBusinessByIdUseCase, switchBusinessFavoriteUseCase)
    }
}