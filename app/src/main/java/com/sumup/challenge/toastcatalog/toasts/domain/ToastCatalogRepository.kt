package com.sumup.challenge.toastcatalog.toasts.domain

import kotlinx.coroutines.flow.Flow
import com.sumup.challenge.toastcatalog.toasts.domain.model.Toast

interface ToastCatalogRepository {
     fun getToasts(): Flow<List<Toast>>
}
