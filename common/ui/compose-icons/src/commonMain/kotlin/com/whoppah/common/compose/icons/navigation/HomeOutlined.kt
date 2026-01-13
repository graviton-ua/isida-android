package com.whoppah.common.compose.icons.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Navigation.HomeOutlined: ImageVector
    get() = Icons.Outlined.Home

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Navigation.HomeOutlined, contentDescription = null)