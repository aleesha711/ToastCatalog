package com.sumup.challenge.toastcatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sumup.challenge.toastcatalog.toasts.presentation.ToastCatalogScreen
import dagger.hilt.android.AndroidEntryPoint
import com.sumup.challenge.toastcatalog.designsystem.theme.ToastCatalogTheme

@AndroidEntryPoint
class ToastsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToastCatalogTheme {
                ToastCatalogScreen()
            }
        }
    }
}
