package com.sumup.challenge.toastcatalog.toasts.data.api

import com.sumup.challenge.toastcatalog.toasts.data.model.ToastCatalogResponse
import retrofit2.http.GET

interface ToastCatalogApiServices {
    @GET(value = "items")
    suspend fun getCatalogs(): List<ToastCatalogResponse>
}