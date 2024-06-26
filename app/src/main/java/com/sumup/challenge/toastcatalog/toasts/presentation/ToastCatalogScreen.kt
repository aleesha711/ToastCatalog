package com.sumup.challenge.toastcatalog.toasts.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.sumup.challenge.toastcatalog.toasts.presentation.component.ItemToast
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.sumup.challenge.toastcatalog.R
import com.sumup.challenge.toastcatalog.designsystem.ErrorSnackBar
import com.sumup.challenge.toastcatalog.designsystem.LoadingFullScreen
import com.sumup.challenge.toastcatalog.designsystem.TopAppBar
import com.sumup.challenge.toastcatalog.toasts.presentation.model.ToastUiModel

/**
 * Full ToastCatalogScreen screen UI
 * @param viewModel injected by hilt that will be used to get ToastCatalogs list that need to be shown
 *
 */
@Composable
fun ToastCatalogScreen(
    viewModel: ToastCatalogViewModel = hiltViewModel()
) {
    val toastCatalogState by viewModel.toastCatalogState.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(title = stringResource(id = R.string.app_name))
        },

        snackbarHost = {
            // snack bar host that will be used later to show snack bar
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { paddingValues ->
        when  {
            isLoading -> LoadingFullScreen()

            toastCatalogState.isSuccess -> {
                val toasts = toastCatalogState.getOrNull()
                toasts?.let { ToastList(toasts = it, contentPadding = paddingValues) }
            }
            toastCatalogState.isFailure ->{
                val errorMessage = toastCatalogState.exceptionOrNull()?.message ?: "Unknown error"
                ErrorSnackBar(
                    errorMessage = errorMessage,
                    actionLabel = stringResource(id = R.string.retry),
                    onRetry = { viewModel.retryLoadToasts() },
                    snackBarHostState = snackBarHostState
                )
            }
        }
    }
}
@Composable
fun ToastList(toasts: List<ToastUiModel>, contentPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier.padding(contentPadding)
    ) {
        items(toasts) { toast ->
            ItemToast(toast)
        }
    }
}