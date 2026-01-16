package com.whoppah.common.permissions

import androidx.compose.runtime.Composable

/**
 * JVM/Desktop implementation of [rememberWriteStorageAction].
 * Desktop operating systems typically allow writing to accessible directories without runtime prompts.
 */
@Composable
actual fun rememberWriteStorageAction(onAction: () -> Unit): () -> Unit {
    // JVM does not require explicit permission to save files to the App Documents directory
    return onAction
}