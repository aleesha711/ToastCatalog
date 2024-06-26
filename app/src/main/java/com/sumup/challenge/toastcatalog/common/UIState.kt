package com.sumup.challenge.toastcatalog.common
/**
 * To handle UI states and exposing data to the views
 *
 */
sealed class UIState<out T> {
    object Loading : UIState<Nothing>()
    data class Success<out T>(val data: T) : UIState<T>()
    data class Error(val message: String) : UIState<Nothing>()
}