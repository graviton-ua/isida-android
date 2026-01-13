package com.whoppah.common.compose.icons.action

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Clear: ImageVector
    get() = Icons.Rounded.Clear

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Action.Clear, contentDescription = null)