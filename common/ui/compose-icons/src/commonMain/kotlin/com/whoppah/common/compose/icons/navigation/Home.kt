package com.whoppah.common.compose.icons.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.whoppah.common.compose.icons.WhIcons

val WhIcons.Navigation.Home: ImageVector
    get() = Icons.Filled.Home

@Preview
@Composable
private fun Preview() = Icon(imageVector = WhIcons.Navigation.Home, contentDescription = null)