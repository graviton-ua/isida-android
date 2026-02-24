package ua.graviton.isida.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.isida.common.ui.compose.theme.IsidaPalette
import ua.isida.common.ui.compose.theme.AppTheme
import ua.isida.common.ui.compose.theme.IsidaTheme

@Composable
internal fun MainBottomBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(color = IsidaPalette.White),
    ) {
        content()
    }
}

@Composable
internal fun Item(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = 2.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable(onClick = onClick),
    ) {
        CompositionLocalProvider(
            LocalContentColor provides if (isSelected) IsidaTheme.colors.secondary else IsidaPalette.Black
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier,
            )
            Text(
                text = title,
                style = IsidaTheme.typography.helper,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        MainBottomBar(
            modifier = Modifier.fillMaxWidth(),
        ) {

        }
    }
}