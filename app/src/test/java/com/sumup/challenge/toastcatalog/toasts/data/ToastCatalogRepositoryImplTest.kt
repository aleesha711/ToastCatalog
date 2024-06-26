package com.sumup.challenge.toastcatalog.toasts.data

import com.TestUtils
import com.sumup.challenge.toastcatalog.common.Mapper
import com.sumup.challenge.toastcatalog.toasts.data.datasource.FetchToastCatalogRemoteDataSource
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
import dagger.Lazy
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
@ExperimentalCoroutinesApi
class ToastCatalogRepositoryImplTest {

    private lateinit var repository: ToastCatalogRepositoryImpl
    private lateinit var fetchToastCatalogRemoteDataSource: FetchToastCatalogRemoteDataSource
    private lateinit var mapper: Mapper<List<ToastCatalogResponse>, List<Toast>>

    @Before
    fun setup() {
        fetchToastCatalogRemoteDataSource = mockk(relaxed = true)
        mapper = mockk()
        val lazyFetchToastCatalogRemoteDataSource = Lazy { fetchToastCatalogRemoteDataSource }
        repository = ToastCatalogRepositoryImpl(lazyFetchToastCatalogRemoteDataSource, mapper)

    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getToasts returns mapped result`() = runTest {

        // Given
        coEvery { fetchToastCatalogRemoteDataSource.getCatalogs() } returns flow { emit(TestUtils.getToastCatalogResponse()) }
        coEvery { mapper.map(TestUtils.getToastCatalogResponse()) } returns TestUtils.getToasts()

        // When
        val result = repository.getToasts().first()

        // Then
        assertEquals(TestUtils.getToasts(), result)

        // Verify that the getCatalogs() function is called once
        coVerify { fetchToastCatalogRemoteDataSource.getCatalogs() }

        // Verify that the map() function is called once with the correct argument
        coVerify { mapper.map(TestUtils.getToastCatalogResponse()) }
    }
}
