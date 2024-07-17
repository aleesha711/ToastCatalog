package com.sumup.challenge.toastcatalog.toasts.domain.usecase

import com.TestUtils
import com.sumup.challenge.toastcatalog.toasts.domain.ToastCatalogRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import io.mockk.clearAllMocks
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Before
import java.io.IOException

class FetchToastCatalogUseCaseImplTest {

    private lateinit var useCase: FetchToastCatalogUseCaseImpl
    private val toastCatalogRepository: ToastCatalogRepository = mockk()

    @Before
    fun setUp() {
        useCase = FetchToastCatalogUseCaseImpl(toastCatalogRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
    @Test
    fun `invoke should Success`() = runTest {

        // Stub the repository to return a flow emitting the mock toasts
        coEvery { toastCatalogRepository.getToasts() } returns flow {
            emit(TestUtils.getToasts())
        }

        // Invoke the use case and get the single result
        val result = useCase.invoke().first()

        // Verify that the result is Success
     //   assertTrue(result is Result.Success)

        val successResult = result

        assertEquals(TestUtils.getToasts(), successResult)
    }
}