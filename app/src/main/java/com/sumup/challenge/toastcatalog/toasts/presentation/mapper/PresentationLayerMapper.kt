package com.sumup.challenge.toastcatalog.toasts.presentation.mapper

import com.sumup.challenge.toastcatalog.common.formatToReadable
import com.sumup.challenge.toastcatalog.toasts.domain.model.Toast
import com.sumup.challenge.toastcatalog.toasts.presentation.model.ToastUiModel
internal fun List<Toast>.toToastUiModelList(): List<ToastUiModel> {
    return this.map { toast ->
        ToastUiModel(
            id = toast.id,
            name = toast.name,
            price = getPrice(toast.price),
            soldAt = toast.soldAt?.formatToReadable()
        )
    }
}

private fun getPrice(price: String) = "$price €"