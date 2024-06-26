package com.sumup.challenge.toastcatalog.toasts.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ToastCatalogResponse(
    @SerialName("currency")
    val currency: String,
    @SerialName("id")
    val id: Int,
    @SerialName("last_sold")
    val lastSold: String,
    @SerialName("name")
    val name: String,
    @SerialName("price")
    val price: String
)