package ua.isida.common.ui.compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import ua.isida.common.ui.compose.LocalWhUriHandler
import ua.isida.common.ui.compose.theme.IsidaTheme

@Composable
fun AppColumnDialog(
    title: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    onClose: (() -> Unit)? = null,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable ColumnScope.() -> Unit,
) = AppColumnDialog(
    onClose = onClose,
    modifier = modifier,
    contentPadding = contentPadding,
    verticalArrangement = verticalArrangement,
) {
    ProvideTextStyle(value = IsidaTheme.typography.h4) {
        title()
    }
    content()
}

@Composable
fun AppColumnDialog(
    modifier: Modifier = Modifier,
    onClose: (() -> Unit)? = null,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable ColumnScope.() -> Unit,
) {
    AppDialog(
        onClose = onClose,
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            modifier = Modifier.padding(contentPadding),
        ) {
            content()
        }
    }
}

@Composable
fun AppDialog(
    modifier: Modifier = Modifier,
    onClose: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .background(color = IsidaTheme.colors.background, shape = IsidaTheme.shapes.medium),
    ) {
        onClose?.let {
            IconButton(
                onClick = it,
                modifier = Modifier.align(Alignment.TopEnd),
            ) { Icon(imageVector = Icons.Default.Close, contentDescription = null) }
        }

        /**
         * Provides a composition local value for [LocalUriHandler] that is guaranteed to be the same instance
         * throughout the composition hierarchy. This is important because [LocalUriHandler] is typically replaced
         * with a new instance each time the content is rendered, leading to potential issues. By using this provider,
         * you can ensure that the original [UriHandler] created by your application is used consistently.
         */
        CompositionLocalProvider(
            LocalUriHandler provides (LocalWhUriHandler.current ?: LocalUriHandler.current)
        ) {
            content()
        }
    }
}