package com.sumup.challenge.toastcatalog.toasts.data.model.mapper

import com.TestUtils
import com.sumup.challenge.toastcatalog.common.serverDateTimeWithToDate
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Test

class ToastCatalogResponseToToastMapperTest {
    @Test
    fun `map ToastCatalogResponse to Toast`() {
        mockkStatic("com.sumup.challenge.toastcatalog.common.DateExtensionsKt") {
            every { any<String>().serverDateTimeWithToDate() } returns TestUtils.sold1
            val mapper = ToastCatalogResponseToToastMapper()
            val result = mapper.map(TestUtils.toastCatalogResponse1)
            assertEquals(TestUtils.toastCatalogResponse1.name, result.name)
            assertEquals(TestUtils.toastCatalogResponse1.price, result.price)
            assertEquals(TestUtils.sold1, result.soldAt)
        }
    }
}