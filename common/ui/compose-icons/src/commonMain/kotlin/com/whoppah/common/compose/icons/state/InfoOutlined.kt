package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.InfoOutlined: ImageVector
    get() = Icons.Outlined.Info

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.InfoOutlined, contentDescription = null)