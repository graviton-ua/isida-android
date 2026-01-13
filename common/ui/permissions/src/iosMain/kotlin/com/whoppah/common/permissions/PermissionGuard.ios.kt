package com.whoppah.common.permissions

import androidx.compose.runtime.Composable

@Composable
actual fun rememberWriteStorageAction(onAction: () -> Unit): () -> Unit {
    // iOS does not require explicit permission to save files to the App Documents directory
    return onAction
}