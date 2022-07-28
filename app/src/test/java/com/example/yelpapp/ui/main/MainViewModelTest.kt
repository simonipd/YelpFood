package com.example.yelpapp.ui.main

import app.cash.turbine.test
import com.example.yelpapp.testrules.CoroutinesTestRule
import com.example.yelpapp.testshared.sampleBusiness
import com.example.yelpapp.ui.main.MainViewModel.*
import com.example.yelpapp.usecases.GetBusinessUseCase
import com.example.yelpapp.usecases.RequestBusinessUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getBusinessUseCase: GetBusinessUseCase

    @Mock
    lateinit var requestBusinessUseCase: RequestBusinessUseCase

    private lateinit var viewModel: MainViewModel

    private val business = listOf(sampleBusiness.copy(id = "id_test"))

    @Before
    fun setup() {
        whenever(getBusinessUseCase()).thenReturn(flowOf(business))
        viewModel = MainViewModel(getBusinessUseCase, requestBusinessUseCase)
    }

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        viewModel.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(businesses = business), awaitItem())
            cancel()
        }
    }


    @Test
    fun `Progress is shown when screen starts and hidden when it finishes requesting business`() =
        runTest {
            viewModel.onUiReady()

            viewModel.state.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(businesses = business, loading = false), awaitItem())
                assertEquals(UiState(businesses = business, loading = true), awaitItem())
                assertEquals(UiState(businesses = business, loading = false), awaitItem())
                cancel()
            }
        }

    @Test
    fun `Popular movies are requested when UI screen starts`() = runTest {
        viewModel.onUiReady()
        runCurrent()

        verify(requestBusinessUseCase).invoke()
    }
}