package com.sumup.challenge.toastcatalog.toasts.data.datasource

import com.TestUtils
import com.sumup.challenge.toastcatalog.toasts.data.api.ToastCatalogApiServices
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class FetchToastCatalogRemoteDatasourceImplTest {

    private lateinit var datasource: FetchToastCatalogRemoteDatasourceImpl
    private lateinit var mockCatalogApi: ToastCatalogApiServices

    @Before
    fun setUp() {
        mockCatalogApi = mockk()
        datasource = FetchToastCatalogRemoteDatasourceImpl(mockCatalogApi)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getCatalogs should return data from API`() = runTest {
        // Mock the response from the API
        coEvery {
            mockCatalogApi.getCatalogs()
        } returns TestUtils.getToastCatalogResponse()

        // Invoke the method and collect the result
        val result = datasource.getCatalogs().first()

        // Verify that the result matches the mock response
        assertEquals(TestUtils.getToastCatalogResponse(), result)
    }
}