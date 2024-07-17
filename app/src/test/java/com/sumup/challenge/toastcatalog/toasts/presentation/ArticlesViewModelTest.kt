package com.sumup.challenge.toastcatalog.toasts.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.TestUtils
import com.sumup.challenge.toastcatalog.toasts.domain.usecase.FetchToastCatalogUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
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
import com.sumup.challenge.toastcatalog.toasts.domain.model.Toast
import com.sumup.challenge.toastcatalog.toasts.presentation.mapper.toToastUiModelList
import com.sumup.challenge.toastcatalog.toasts.presentation.model.ToastUiModel
import org.junit.Assert.assertTrue

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
    fun `verify initial loading state`() = runTest {
            // Assert that the initial loading state is true
            assertEquals(true, viewModel.loading.value)
    }

    @Test
    fun `fetchToastCatalog success`() = runTest {
        turbineScope {
            // Create a flow emitting the success result
            val successResult = flow {
                emit(TestUtils.getToasts())
            }

            // Stub the use case to return the success result
            coEvery { fetchToastCatalogUseCase.invoke() } returns successResult
            // Use Turbine to collect and verify the StateFlow emissions
            viewModel.loading.test {

                // Assert that the initial loading state is true
                assertEquals(true, awaitItem())

                // Trigger fetching
                viewModel.fetchToastCatalog()

                // Assert that the loading state changes to false after fetch completes
                assertEquals(false, awaitItem())
            }

            viewModel.toastCatalogState.test {
                // Assert that the initial state is empty list
                assertEquals(emptyList<ToastUiModel>(), awaitItem().getOrNull())

                // Trigger fetching
                viewModel.fetchToastCatalog()

                // Assert that the next emission is the success state with the expected toast list
                assertEquals(TestUtils.getToasts().toToastUiModelList(), awaitItem().getOrNull())
            }

            // Verify the interaction with the use case
            coVerify { fetchToastCatalogUseCase.invoke() }
        }
    }

    @Test
    fun `fetchToastCatalog error`() = runTest(testDispatcher) {
        val errorMessage = "Failed to fetch toasts"

        // Create a flow emitting the error result
        val errorResult = flow<List<Toast>> {
            throw Exception(errorMessage)
        }

        // Stub the use case to return the error result
        coEvery { fetchToastCatalogUseCase.invoke() } returns errorResult

        // Use Turbine to collect and verify the StateFlow emissions
        viewModel.toastCatalogState.test {
            // Trigger fetching
            viewModel.fetchToastCatalog()

            // Assert that the initial state is an empty success list
            val initialResult = awaitItem()
            assertTrue(initialResult.isSuccess)
            assertTrue(initialResult.getOrNull()?.isEmpty() == true)

            // Assert that the next emission is the error state
            val result = awaitItem()
            assertTrue(result.isFailure)
            assertEquals(errorMessage, result.exceptionOrNull()?.message)

            // Verify the interaction with the use case
            coVerify { fetchToastCatalogUseCase.invoke() }
        }

        // Check the loading state
        viewModel.loading.test {
            // Initial loading state
            //      assertEquals(true, awaitItem())

            // Loading state after fetch attempt
            assertEquals(false, awaitItem())
        }
    }
}
