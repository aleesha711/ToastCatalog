package com.sumup.challenge.toastcatalog.toasts.domain.usecase

import kotlinx.coroutines.flow.Flow
import com.sumup.challenge.toastcatalog.toasts.domain.model.Toast

/**
 * usecase to fetch toasts
 */
interface FetchToastCatalogUseCase {
    operator fun invoke(): Flow<List<Toast>>
}
