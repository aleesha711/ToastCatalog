package com.sumup.challenge.toastcatalog.designsystem

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun ErrorSnackBar(errorMessage: String,
                  actionLabel: String,
                  onRetry: () -> Unit,
                  snackBarHostState: SnackbarHostState) {
    LaunchedEffect(key1 = snackBarHostState) {
        val result = snackBarHostState.showSnackbar(
            message = errorMessage,
            actionLabel = actionLabel
        )
        when (result) {
            SnackbarResult.ActionPerformed -> onRetry()
            SnackbarResult.Dismissed -> Unit // Optional: Handle dismissal action if needed
        }
    }
}