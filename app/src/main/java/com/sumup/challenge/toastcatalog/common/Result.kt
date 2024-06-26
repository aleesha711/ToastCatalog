package com.sumup.challenge.toastcatalog.common

/**
Represents the result of an asynchronous operation that can either succeed or fail.
This sealed class is used to wrap the outcome of operations to provide a standardized
way of handling success and error cases.
Success: Contains the result data when the operation succeeds.
Error: Contains an exception when the operation fails.
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}