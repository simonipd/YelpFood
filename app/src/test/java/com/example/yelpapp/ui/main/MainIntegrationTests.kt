package com.example.yelpapp.ui.main

import app.cash.turbine.test
import com.example.yelpapp.buildLocalBusiness
import com.example.yelpapp.buildRemoteBusiness
import com.example.yelpapp.buildRepositoryWith
import com.example.yelpapp.data.server.model.RemoteBusiness
import com.example.yelpapp.testrules.CoroutinesTestRule
import com.example.yelpapp.ui.main.MainViewModel.*
import com.example.yelpapp.usecases.GetBusinessUseCase
import com.example.yelpapp.usecases.RequestBusinessUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

import com.example.yelpapp.data.database.model.Business as LocalBusiness

@ExperimentalCoroutinesApi
class MainIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server when local is empty`() = runTest {
        val remoteData = buildRemoteBusiness("1","2","3")
        val vm = buildViewModelWith(
            localData = emptyList(),
            remoteData = remoteData)

        vm.onUiReady()

        vm.state.test {
            assertEquals(UiState(),awaitItem())
            assertEquals(UiState(businesses = emptyList()),awaitItem())
            assertEquals(UiState(businesses = emptyList(), loading = true),awaitItem())
            assertEquals(UiState(businesses = emptyList(), loading = false),awaitItem())

            val business = awaitItem().businesses!!
            assertEquals("1",business[0].id)
            assertEquals("2",business[1].id)
            assertEquals("3",business[2].id)

            cancel()
        }
    }

    @Test
    fun `data is loaded from local when available`() = runTest {
        val localData = buildLocalBusiness("1","2","3")
        val remoteData = buildRemoteBusiness("4","5","6")
        val vm = buildViewModelWith(localData, remoteData)

        vm.state.test {
            assertEquals(UiState(),awaitItem())

            val business = awaitItem().businesses!!
            assertEquals("1",business[0].id)
            assertEquals("2",business[1].id)
            assertEquals("3",business[2].id)

            cancel()
        }
    }

    private fun buildViewModelWith(
        localData : List<LocalBusiness> = emptyList(),
        remoteData : List<RemoteBusiness> = emptyList(),
    ) : MainViewModel{

        val businessRepository = buildRepositoryWith(localData,remoteData)
        val getBusinessUseCase = GetBusinessUseCase(businessRepository)
        val requestBusinessUseCase = RequestBusinessUseCase(businessRepository)
        return MainViewModel(getBusinessUseCase, requestBusinessUseCase)
    }
}