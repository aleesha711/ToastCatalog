package com.sumup.challenge.toastcatalog.toasts.data

import com.sumup.challenge.toastcatalog.toasts.data.model.ToastCatalogResponse
import kotlinx.coroutines.flow.Flow
import com.sumup.challenge.toastcatalog.common.Mapper
import com.sumup.challenge.toastcatalog.toasts.data.api.ToastCatalogApiServices
import com.sumup.challenge.toastcatalog.toasts.domain.ToastCatalogRepository
import com.sumup.challenge.toastcatalog.toasts.domain.model.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

/**
 * Toast Catalog repository responsible of getting the data from data layer and
 * prepare it (do any mapping needed) to be used in domain/business layer
 *
 */
internal class ToastCatalogRepositoryImpl @Inject constructor(
    private val catalogApi: ToastCatalogApiServices,
    private val mapper: Mapper<List<ToastCatalogResponse>, List<Toast>>
) : ToastCatalogRepository {
    override fun getToasts(): Flow<List<Toast>> = flow {
        val response = catalogApi.getCatalogs()
        emit(mapper.map(response))
    }.catch { e ->
        when (e) {
            is IOException -> throw IOException("Network error: Unable to fetch toasts", e)
            else -> throw Exception("${e.message}", e)
        }
    }.flowOn(Dispatchers.IO)
}