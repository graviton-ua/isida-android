package com.whoppah.common.permissions

import androidx.compose.runtime.Composable

/**
 * Returns a lambda that, when invoked, checks for storage permissions
 * (if necessary on the current platform) and then executes [onAction].
 */
@Composable
expect fun rememberWriteStorageAction(onAction: () -> Unit): () -> Unit