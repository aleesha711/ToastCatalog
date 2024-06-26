package com.sumup.challenge.toastcatalog.toasts.data.di

import com.sumup.challenge.toastcatalog.toasts.data.ToastCatalogRepositoryImpl
import com.sumup.challenge.toastcatalog.toasts.data.api.ToastCatalogApiServices
import com.sumup.challenge.toastcatalog.toasts.data.datasource.FetchToastCatalogRemoteDataSource
import com.sumup.challenge.toastcatalog.toasts.data.datasource.FetchToastCatalogRemoteDatasourceImpl
import com.sumup.challenge.toastcatalog.toasts.data.model.ToastCatalogResponse
import com.sumup.challenge.toastcatalog.common.Mapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.sumup.challenge.toastcatalog.toasts.data.model.mapper.ToastCatalogResponseToToastMapper
import com.sumup.challenge.toastcatalog.toasts.data.model.mapper.ListMapper
import com.sumup.challenge.toastcatalog.toasts.domain.ToastCatalogRepository
import com.sumup.challenge.toastcatalog.toasts.domain.model.Toast
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Hilt Module for Data layer dependencies provider and binds
 *
 */
@Module
@InstallIn(SingletonComponent::class)
internal interface ToastCatalogDataModule {

    /**
     * binds toasts repository interface with its implementation so it can be injected
     *
     * @param repository the implementation for toasts repository that will be injected
     *
     * @return the interface that other classes will depend on
     *
     */
    @Binds
    fun bindsToastCatalogRepository(
        repository: ToastCatalogRepositoryImpl
    ): ToastCatalogRepository

    @Binds
    fun bindsFetchToastCatalogRemoteDataSource(
        fetchCatalogRemoteDatasourceImpl: FetchToastCatalogRemoteDatasourceImpl
    ): FetchToastCatalogRemoteDataSource

    /**
     * binds mapper that map toasts response received from APT to toast (Domain/Business model)
     *
     * @param mapper the implementation for toasts response mapper that will be injected
     *
     * @return the interface that other classes will depend on
     *
     */
    @Binds
    fun bindToastCatalogResponseToToastMapper(
        mapper: ToastCatalogResponseToToastMapper
    ): Mapper<ToastCatalogResponse, Toast>

    companion object {
        /**
         * Provider fuction that provide the toasts api service
         * that can be injected in any datasource
         *
         * @param retrofit the retrofit configuration that will be use to create API service
         * @return ToastCatalogApiServices that can be use to call the toasts API
         *
         */
        @Singleton
        @Provides
        fun provideToastCatalogApiServices(
            retrofit: Retrofit
        ): ToastCatalogApiServices {
            return retrofit.create(ToastCatalogApiServices::class.java)
        }

        @Singleton
        @Provides
        fun provideListMapper(
            mapper: Mapper<ToastCatalogResponse, Toast>
        ): Mapper<List<ToastCatalogResponse>, List<Toast>> {
            return ListMapper(mapper)
        }
    }
}
