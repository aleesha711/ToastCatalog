package com.sumup.challenge.toastcatalog.toasts.data

import com.sumup.challenge.toastcatalog.toasts.data.datasource.FetchToastCatalogRemoteDataSource
import com.sumup.challenge.toastcatalog.toasts.data.model.ToastCatalogResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.sumup.challenge.toastcatalog.common.Mapper
import com.sumup.challenge.toastcatalog.toasts.domain.ToastCatalogRepository
import com.sumup.challenge.toastcatalog.toasts.domain.model.Toast
import javax.inject.Inject
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.io.IOException

/**
 * Toast Catalog repository responsible of getting the data from data layer and
 * prepare it (do any mapping needed) to be used in domain/business layer
 *
 */
internal class ToastCatalogRepositoryImpl @Inject constructor(
    private val fetchAirlinesRemoteDatasource: Lazy<FetchToastCatalogRemoteDataSource>,
    private val mapper: Mapper<List<ToastCatalogResponse>, List<Toast>>
) : ToastCatalogRepository {

    override fun getToasts(): Flow<List<Toast>> {
        return fetchAirlinesRemoteDatasource.get().getCatalogs()
            .map { response ->
                mapper.map(response)
            }
            .catch { e ->
                when (e) {
                    is IOException ->  throw IOException("Network error: Unable to fetch toasts.", e)
                    else -> throw Exception("Unknown error: ${e.message}", e)
                }
            }.flowOn(Dispatchers.IO)
    }
}