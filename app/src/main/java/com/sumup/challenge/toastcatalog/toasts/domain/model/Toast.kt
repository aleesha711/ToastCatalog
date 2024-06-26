package com.sumup.challenge.toastcatalog.toasts.domain.model

import java.time.LocalDateTime

/**
 * Domain layer model for Toast that will be used for business logic
 *
 */
data class Toast(
    val id: Int,
    val name: String,
    val price: String,
    val soldAt: LocalDateTime?
)
