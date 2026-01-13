package com.whoppah.common.compose.icons.action

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Action.Add: ImageVector
    get() = Icons.Rounded.Add

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Action.Add, contentDescription = null)