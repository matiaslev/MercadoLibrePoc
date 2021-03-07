package com.matiaslev.mercadolibrepoc

import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun Details(url: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                loadUrl(url)
            }
        }
    )
}