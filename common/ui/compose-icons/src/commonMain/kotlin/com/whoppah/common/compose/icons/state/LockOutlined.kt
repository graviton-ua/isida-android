package com.whoppah.common.compose.icons.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.State.LockOutlined: ImageVector
    get() = Icons.Outlined.Lock

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.State.LockOutlined, contentDescription = null)