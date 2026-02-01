package com.whoppah.common.resources

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource

// @Composable
// fun stringResource(slug: String): String {
//     val formatted = slug.replace("-", "_")
//     return Res.allStringResources[formatted]?.let { stringResource(it) } ?: formatted
// }
//
// @Composable
// fun stringResource(slug: String, vararg formatArgs: Any?): String {
//     val formatted = slug.replace("-", "_")
//     return Res.allStringResources[formatted]?.let { stringResource(it, formatArgs) } ?: formatted
// }