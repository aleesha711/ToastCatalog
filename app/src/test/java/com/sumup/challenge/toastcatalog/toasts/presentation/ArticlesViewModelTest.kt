package com.sumup.challenge.toastcatalog.toasts.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.TestUtils
import com.sumup.challenge.toastcatalog.common.UIState
import com.sumup.challenge.toastcatalog.toasts.domain.usecase.FetchToastCatalogUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.sumup.challenge.toastcatalog.common.Result
import com.sumup.challenge.toastcatalog.toasts.presentation.mapper.toToastUiModelList
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Rule
import app.cash.turbine.test
import app.cash.turbine.turbineScope


@OptIn(ExperimentalCoroutinesApi::class)
class ToastCatalogViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val fetchToastCatalogUseCase: FetchToastCatalogUseCaseImpl = mockk(relaxed = true)
    private lateinit var viewModel: ToastCatalogViewModel

    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ToastCatalogViewModel(fetchToastCatalogUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `fetchToastCatalog success`() = runTest {
        turbineScope {
            // Define the expected toast data
            val expectedToasts = TestUtils.getToasts()

            // Create a flow emitting the success result
            val successResult = flow {
                emit(Result.Success(expectedToasts))
            }

            // Stub the use case to return the success result
            coEvery { fetchToastCatalogUseCase.invoke() } returns successResult

            // Use Turbine to collect and verify the StateFlow emissions
            viewModel.toastCatalogState.test {
                // Trigger fetching
                viewModel.fetchToastCatalog()

                // Assert that the initial state is Loading
                assertEquals(UIState.Loading, awaitItem())

                // Assert that the final state is Success with the expected toasts
                assertEquals(UIState.Success(expectedToasts.toToastUiModelList()), awaitItem())

                // Verify the interaction with the use case
                coVerify { fetchToastCatalogUseCase.invoke() }
            }
        }
    }

    @Test
    fun `fetchToastCatalog error`() = runTest {
        turbineScope {
            // Define the error message
            val errorMessage = "Failed to fetch toasts"

            // Create a flow emitting the error result
            val errorResult = flow {
                emit(Result.Error(Exception(errorMessage)))
            }

            // Stub the use case to return the error result
            coEvery { fetchToastCatalogUseCase.invoke() } returns errorResult

            // Use Turbine to collect and verify the StateFlow emissions
            viewModel.toastCatalogState.test {

                // Trigger fetching
                viewModel.fetchToastCatalog()

                // Assert that the initial state is Loading
                assertEquals(UIState.Loading, awaitItem())

                // Assert that the final state is Error with the expected error message
                assertEquals(UIState.Error(errorMessage), awaitItem())

                // Verify the interaction with the use case
                coVerify { fetchToastCatalogUseCase.invoke() }
            }
        }
    }
}