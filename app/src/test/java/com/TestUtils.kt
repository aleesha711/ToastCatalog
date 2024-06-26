package com

import com.sumup.challenge.toastcatalog.toasts.data.model.ToastCatalogResponse
import com.sumup.challenge.toastcatalog.toasts.domain.model.Toast
import com.sumup.challenge.toastcatalog.toasts.presentation.model.ToastUiModel
import java.time.LocalDateTime

internal object TestUtils {

    val sold1: LocalDateTime = LocalDateTime.of(2024, 1, 18, 20, 31, 44)

    val toastCatalogResponse1 = ToastCatalogResponse(
        currency = "USD",
        id = 1,
        lastSold = "2024-05-25T10:15:30",
        name = "Toast 1",
        price = "45"
    )

    private val toastCatalogResponse2 = ToastCatalogResponse(
        currency = "USD",
        id = 2,
        lastSold = "2024-05-25T10:15:30",
        name = "Toast 1",
        price = "50"
    )

    private val toast1 = Toast(
        id = 1,
        name = "Toast 1",
        price = "45",
        soldAt = sold1
    )
    private val toast2 = Toast(
        id = 2,
        name = "Toast 2",
        price = "50",
        soldAt =  LocalDateTime.of(2024, 1, 18, 18, 39, 17)
    )

    fun getToasts() = listOf(toast1, toast2)
    fun getToastCatalogResponse() = listOf(toastCatalogResponse1, toastCatalogResponse2)
}