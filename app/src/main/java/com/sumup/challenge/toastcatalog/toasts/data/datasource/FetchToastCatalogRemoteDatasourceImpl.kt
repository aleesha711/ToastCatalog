package com.sumup.challenge.toastcatalog.toasts.data.datasource

import com.sumup.challenge.toastcatalog.toasts.data.api.ToastCatalogApiServices
import com.sumup.challenge.toastcatalog.toasts.data.model.ToastCatalogResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchToastCatalogRemoteDatasourceImpl @Inject constructor(private val catalogApi: ToastCatalogApiServices) :
    FetchToastCatalogRemoteDataSource {
    override fun getCatalogs(): Flow<List<ToastCatalogResponse>> = flow {
        emit(catalogApi.getCatalogs())
    }
}