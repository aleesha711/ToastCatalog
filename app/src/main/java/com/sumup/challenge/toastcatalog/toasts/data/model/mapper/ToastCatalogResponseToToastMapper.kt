package com.sumup.challenge.toastcatalog.toasts.data.model.mapper

import com.sumup.challenge.toastcatalog.toasts.data.model.ToastCatalogResponse
import com.sumup.challenge.toastcatalog.common.Mapper
import com.sumup.challenge.toastcatalog.toasts.domain.model.Toast
import com.sumup.challenge.toastcatalog.common.serverDateTimeWithToDate
import javax.inject.Inject

/**
 * mapper that maps ToastCatalogResponse model to Toast
 *
 */
internal class ToastCatalogResponseToToastMapper @Inject constructor() :
    Mapper<ToastCatalogResponse, Toast> {

    /**
     * mapping function that return domain model of the Article
     *
     * @param from is the ToastCatalogResponse that we want to map it to Toast
     * @return Toast model
     */
    override fun map(from: ToastCatalogResponse) = Toast (
        id = from.id,
        name = from.name,
        price = from.price,
        // convert string date to LocalDateTime that can be used in business logic
        soldAt = from.lastSold.serverDateTimeWithToDate(),
    )
}

internal class ListMapper<I, O> @Inject constructor(
    private val mapper: Mapper<I, O>
) : Mapper<List<I>, List<O>> {
    override fun map(from: List<I>): List<O> {
        return from.map { item -> mapper.map(item) }
    }
}