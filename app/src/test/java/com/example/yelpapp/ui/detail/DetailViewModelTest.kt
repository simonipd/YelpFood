package com.example.yelpapp.ui.detail

import app.cash.turbine.test
import com.example.yelpapp.testrules.CoroutinesTestRule
import com.example.yelpapp.testshared.sampleBusiness
import com.example.yelpapp.ui.detail.DetailViewModel.UiState
import com.example.yelpapp.usecases.GetBusinessByIdUseCase
import com.example.yelpapp.usecases.SwitchBusinessFavoriteUseCase
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
class DetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getBusinessByIdUseCase: GetBusinessByIdUseCase

    @Mock
    lateinit var switchBusinessFavoriteUseCase: SwitchBusinessFavoriteUseCase

    private lateinit var vm: DetailViewModel

    private val business = sampleBusiness.copy(id = "3")

    @Before
    fun setUp() {
        whenever(getBusinessByIdUseCase("3")).thenReturn(flowOf(business))
        vm = DetailViewModel("3", getBusinessByIdUseCase, switchBusinessFavoriteUseCase)
    }

    @Test
    fun `Ui is updated with the moview on start`() = runTest {
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(business = business), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Favorite action calls the corresponding use case`() = runTest {
        vm.onFavoriteClicked()
        runCurrent()

        verify(switchBusinessFavoriteUseCase).invoke(business)
    }
}