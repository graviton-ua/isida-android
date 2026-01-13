package com.whoppah.common.compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.whoppah.common.compose.icons.WhIcons
import com.whoppah.common.compose.icons.theme.LightBulbIdea
import com.whoppah.common.compose.theme.WhoppahPalette
import com.whoppah.common.compose.theme.WhoppahTheme

@Composable
fun WhInfo(
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 16.dp),
    icon: ImageVector = WhIcons.Theme.LightBulbIdea,
    contentColor: Color = WhoppahPalette.Burst900,
    backgroundColor: Color = WhoppahPalette.Burst50,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement,
        modifier = modifier
            .background(color = backgroundColor, shape = WhoppahTheme.shapes.medium)
            .padding(contentPadding),
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            Icon(
                imageVector = icon,
                contentDescription = null,
            )

            content()
        }
    }
}