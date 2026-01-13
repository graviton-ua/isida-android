package com.whoppah.common.compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import com.whoppah.common.compose.LocalWhUriHandler
import com.whoppah.common.compose.icons.WhIcons
import com.whoppah.common.compose.icons.navigation.Cross
import com.whoppah.common.compose.theme.WhoppahTheme

@Composable
fun WhColumnDialog(
    title: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    onClose: (() -> Unit)? = null,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable ColumnScope.() -> Unit,
) = WhColumnDialog(
    onClose = onClose,
    modifier = modifier,
    contentPadding = contentPadding,
    verticalArrangement = verticalArrangement,
) {
    ProvideTextStyle(value = WhoppahTheme.typography.h4) {
        title()
    }
    content()
}

@Composable
fun WhColumnDialog(
    modifier: Modifier = Modifier,
    onClose: (() -> Unit)? = null,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable ColumnScope.() -> Unit,
) {
    WhDialog(
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
fun WhDialog(
    modifier: Modifier = Modifier,
    onClose: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .background(color = WhoppahTheme.colors.background, shape = WhoppahTheme.shapes.medium),
    ) {
        onClose?.let {
            IconButton(
                onClick = it,
                modifier = Modifier.align(Alignment.TopEnd),
            ) { Icon(imageVector = WhIcons.Navigation.Cross, contentDescription = null) }
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