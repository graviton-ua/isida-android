package ua.graviton.isida.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whoppah.common.compose.theme.WhoppahTheme

/**
 * A wrapper around [TopAppBar] which supports the setting of [contentPadding] to add
 * internal padding. This is especially useful in conjunction with insets.
 *
 * For an edge-to-edge layout, typically you would use the
 * [com.google.accompanist.insets.WindowInsets.systemBars] insets like so below:
 *
 * @sample com.google.accompanist.sample.insets.TopAppBar_Insets
 */
@Composable
fun TopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = WhoppahTheme.colors.primaryContainer,
    contentColor: Color = contentColorFor(backgroundColor),
) {
    TopAppBarSurface(
        modifier = modifier,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
    ) {
        TopAppBarContent(
            title = title,
            navigationIcon = navigationIcon,
            actions = actions,
            modifier = Modifier.padding(contentPadding)
        )
    }
}

@Composable
fun TopAppBarSurface(
    modifier: Modifier = Modifier,
    backgroundColor: Color = WhoppahTheme.colors.primaryContainer,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable () -> Unit,
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        modifier = modifier,
        content = content
    )
}

@Composable
fun TopAppBarContent(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        backgroundColor = Color.Transparent,
        modifier = modifier
    )
}