package com.sumup.challenge.toastcatalog.toasts.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.sumup.challenge.toastcatalog.toasts.domain.usecase.FetchToastCatalogUseCase
import com.sumup.challenge.toastcatalog.toasts.domain.usecase.FetchToastCatalogUseCaseImpl

/**
 * Hilt Module for Domain/Business layer dependencies provider and binds
 *
 */
@Module
@InstallIn(SingletonComponent::class)
interface ToastCatalogDomainModule {

    /**
     * binds toasts usecase interface with its implementation so it can be injected
     *
     * @param usecase the implementation for toasts usecase that will be injected
     *
     * @return the interface that other classes will depend on
     *
     */
    @Binds
    fun bindsFetchToastCatalogUseCase(
        usecase: FetchToastCatalogUseCaseImpl
    ): FetchToastCatalogUseCase
}
