package com.sumup.challenge.toastcatalog.toasts.domain.usecase

import kotlinx.coroutines.flow.Flow
import com.sumup.challenge.toastcatalog.toasts.domain.ToastCatalogRepository
import com.sumup.challenge.toastcatalog.toasts.domain.model.Toast
import javax.inject.Inject

/**
 * usecase to fetch toasts using toasts catalog repository
 */
class FetchToastCatalogUseCaseImpl @Inject constructor(
    private val toastCatalogRepository: ToastCatalogRepository
) : FetchToastCatalogUseCase {
    override fun invoke(): Flow<List<Toast>> = toastCatalogRepository.getToasts()
}


