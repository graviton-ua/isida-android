package com.whoppah.common.permissions

import androidx.compose.runtime.Composable

/**
 * Creates a wrapped version of [onAction] that ensures storage write permissions are granted before execution.
 *
 * @param onAction The action to perform once permission is secured.
 * @return A lambda that checks/requests permission and then runs [onAction].
 * Example:
 * ```
 * val saveAction = rememberWriteStorageAction { saveData() }
 * Button(onClick = saveAction) { Text("Save") }
 * ```
 */
@Composable
expect fun rememberWriteStorageAction(onAction: () -> Unit): () -> Unit