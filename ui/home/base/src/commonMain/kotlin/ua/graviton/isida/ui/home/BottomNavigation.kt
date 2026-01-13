package ua.graviton.isida.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whoppah.common.compose.theme.WhoppahTheme

/**
 * A wrapper around [BottomNavigation] which supports the setting of [contentPadding] to add
 * internal padding. This is especially useful in conjunction with insets.
 *
 * For an edge-to-edge layout, typically you would use the
 * [com.google.accompanist.insets.WindowInsets.navigationBars] insets like so below:
 *
 * @sample com.google.accompanist.sample.insets.BottomNavigation_Insets
 */
@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    backgroundColor: Color = WhoppahTheme.colors.primaryContainer,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable RowScope.() -> Unit,
) {
    BottomNavigationSurface(modifier, backgroundColor, contentColor) {
        BottomNavigationContent(Modifier.padding(contentPadding)) {
            content()
        }
    }
}

@Composable
fun BottomNavigationSurface(
    modifier: Modifier = Modifier,
    backgroundColor: Color = WhoppahTheme.colors.primaryContainer,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable () -> Unit
) {
    Surface(
        color = backgroundColor,
        contentColor = contentColor,
        modifier = modifier,
        content = content,
    )
}

@Composable
fun BottomNavigationContent(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(BottomNavigationHeight)
            .selectableGroup(),
        horizontalArrangement = Arrangement.SpaceBetween,
        content = content,
    )
}

/**
 * Copied from [androidx.compose.material.BottomNavigationHeight]
 * Height of a [BottomNavigation] component
 */
private val BottomNavigationHeight = 56.dp