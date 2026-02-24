@file:Suppress("SameParameterValue")

package com.whoppah.common.compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.whoppah.common.compose.theme.WhoppahPalette
import com.whoppah.common.compose.theme.WhoppahTheme

/**
 * A wrapper around [WhTopAppBar] which supports the setting of [contentPadding] to add
 * internal padding. This is especially useful in conjunction with insets.
 *
 * For an edge-to-edge layout, typically you would use the
 * [com.google.accompanist.insets.WindowInsets.systemBars] insets like so below:
 */
@Composable
fun WhTopAppBar(
    title: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = WhoppahTheme.colors.background,
    contentColor: Color = contentColorFor(backgroundColor = WhoppahTheme.colors.background),
    drawDivider: Boolean = true,
) {
    AppBar(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        drawDivider = drawDivider,
        dividerColor = WhoppahPalette.Grey300,
        contentPadding = contentPadding,
        modifier = modifier,
    ) {
        if (navigationIcon != null) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                navigationIcon()
            }
        }

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProvideTextStyle(
                value = WhoppahTheme.typography.h5,
            ) { title() }
        }

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            content = actions,
        )
    }
}

@Composable
fun WhCustomTopAppBar(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    navigationIcon: @Composable (RowScope.() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = WhoppahTheme.colors.background,
    contentColor: Color = contentColorFor(backgroundColor = WhoppahTheme.colors.background),
    drawDivider: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    AppBar(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        drawDivider = drawDivider,
        dividerColor = WhoppahPalette.Grey300,
        contentPadding = contentPadding,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.matchParentSize(),
        ) {
            if (navigationIcon != null) {
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    content = navigationIcon,
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                content = content,
            )

            Row(
                modifier = Modifier.fillMaxHeight(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                content = actions,
            )
        }
    }
}

@Composable
private fun AppBar(
    backgroundColor: Color,
    contentColor: Color,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    drawDivider: Boolean = true,
    dividerColor: Color = Color.LightGray,
    dividerThickness: Dp = 1.dp,
    content: @Composable BoxScope.() -> Unit
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
    ) {
        Box(
            content = content,
            modifier = modifier
                .fillMaxWidth()
                .background(color = backgroundColor)
                // Draw divider
                .bottomDivider(enabled = drawDivider, strokeWidth = dividerThickness, color = dividerColor)
                //.padding(TopAppBarDefaults.ContentPadding)
                .padding(contentPadding)
                .height(AppBarHeight),
        )
    }
}

private val AppBarHeight = 56.dp

fun Modifier.bottomDivider(enabled: Boolean, strokeWidth: Dp, color: Color) = composed(
    factory = {
        val strokeWidthPx = with(LocalDensity.current) { strokeWidth.toPx() }
        if (!enabled) return@composed this
        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx / 2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)