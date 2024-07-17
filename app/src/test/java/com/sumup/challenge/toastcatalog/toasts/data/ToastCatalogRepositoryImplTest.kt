package com.sumup.challenge.toastcatalog.toasts.data

import com.TestUtils
import com.sumup.challenge.toastcatalog.common.Mapper
import com.sumup.challenge.toastcatalog.toasts.data.api.ToastCatalogApiServices
import com.sumup.challenge.toastcatalog.toasts.data.model.ToastCatalogResponse
import com.sumup.challenge.toastcatalog.toasts.domain.model.Toast
import io.mockk.clearAllMocks
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Before
import org.junit.Test
import io.mockk.coEvery
import io.mockk.coVerify
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import java.io.IOException

@ExperimentalCoroutinesApi
class ToastCatalogRepositoryImplTest {

    private lateinit var repository: ToastCatalogRepositoryImpl
    private lateinit var catalogApi: ToastCatalogApiServices
    private lateinit var mapper: Mapper<List<ToastCatalogResponse>, List<Toast>>

    @Before
    fun setup() {
        catalogApi = mockk(relaxed = true)
        mapper = mockk()
     //   val lazyFetchToastCatalogRemoteDataSource = Lazy { catalogApi }
        repository = ToastCatalogRepositoryImpl(catalogApi, mapper)

    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getToasts returns mapped result`() = runTest {

        // Given
        coEvery { catalogApi.getCatalogs() } returns TestUtils.getToastCatalogResponse()
        coEvery { mapper.map(TestUtils.getToastCatalogResponse()) } returns TestUtils.getToasts()

        // When
        val result = repository.getToasts().first()

        // Then
        assertEquals(TestUtils.getToasts(), result)

        // Verify that the getCatalogs() function is called once
        coVerify { catalogApi.getCatalogs() }

        // Verify that the map() function is called once with the correct argument
        coVerify { mapper.map(TestUtils.getToastCatalogResponse()) }
    }

    @Test
    fun `getToasts handles empty response`() = runTest {
        // Given
        coEvery { catalogApi.getCatalogs() } returns emptyList()
        coEvery { mapper.map(emptyList()) } returns emptyList()

        // When
        val result = repository.getToasts().first()

        // Then
        assertEquals(emptyList<Toast>(), result)

        // Verify that the getCatalogs() function is called once
        coVerify { catalogApi.getCatalogs() }

        // Verify that the map() function is called once with an empty list
        coVerify(exactly = 1) { mapper.map(emptyList()) }
    }

    @Test
    fun `getToasts handles network error`() = runTest {
        // Given
        val errorMessage = "Network error: Unable to fetch toasts"
        coEvery { catalogApi.getCatalogs() } throws IOException(errorMessage)

        // When
        val result = kotlin.runCatching { repository.getToasts().first() }

        // Then
        assertTrue(result.isFailure)
        assertEquals(IOException::class.java, result.exceptionOrNull()!!::class.java)
        assertEquals(errorMessage, result.exceptionOrNull()!!.message)

        // Verify that the getCatalogs() function is called once
        coVerify { catalogApi.getCatalogs() }
    }

    @Test
    fun `getToasts handles unknown error`() = runTest {
        // Given
        val unknownErrorMessage = "Unknown error occurred"
        coEvery { catalogApi.getCatalogs() } throws Exception(unknownErrorMessage)

        // When
        val result = kotlin.runCatching { repository.getToasts().first() }

        // Then
        assertTrue(result.isFailure)
        assertEquals(Exception::class.java, result.exceptionOrNull()!!::class.java)
        assertEquals(unknownErrorMessage, result.exceptionOrNull()!!.message)

        // Verify that the getCatalogs() function is called once
        coVerify { catalogApi.getCatalogs() }
    }
}
