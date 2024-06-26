package com.sumup.challenge.toastcatalog.toasts.data.datasource

import com.sumup.challenge.toastcatalog.toasts.data.model.ToastCatalogResponse
import kotlinx.coroutines.flow.Flow

interface FetchToastCatalogRemoteDataSource {
     fun getCatalogs(): Flow<List<ToastCatalogResponse>>
}