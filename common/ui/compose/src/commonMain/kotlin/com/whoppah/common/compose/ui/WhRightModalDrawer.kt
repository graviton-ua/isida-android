package com.whoppah.common.compose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.whoppah.common.compose.theme.WhoppahTheme

@Composable
fun WhRightModalDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    gesturesEnabled: Boolean = true,
    drawerShape: Shape = WhoppahTheme.shapes.large,
    drawerContainerColor: Color = WhoppahTheme.colors.surface,
    drawerContentColor: Color = contentColorFor(drawerContainerColor),
    scrimColor: Color = DrawerDefaults.scrimColor,
    drawerContent: @Composable ColumnScope.() -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            modifier = modifier,
            drawerState = drawerState,
            gesturesEnabled = gesturesEnabled,
            scrimColor = scrimColor,
            drawerContent = {
                ModalDrawerSheet(
                    drawerShape = drawerShape,
                    drawerContainerColor = drawerContainerColor,
                    drawerContentColor = drawerContentColor,
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        // under the hood, drawerContent is wrapped in a Column, but it would be under the Rtl layout
                        // so we create new column filling max width under the Ltr layout
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            content = drawerContent
                        )
                    }
                }
            },
            content = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Box { content() }
                }
            },
        )
    }
}