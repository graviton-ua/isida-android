package com.whoppah.common.compose.ui

//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp
import com.whoppah.common.compose.icons.WhIcons
import com.whoppah.common.compose.icons.state.Checkmark
import com.whoppah.common.compose.theme.WhoppahPalette
import com.whoppah.common.compose.theme.WhoppahTheme

@Composable
fun WhTag(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(6.dp, alignment = Alignment.Start),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    checkMark: (@Composable AnimatedVisibilityScope.(selected: Boolean) -> Unit)? = WhTagDefaults.checkMark(),
    content: @Composable RowScope.() -> Unit,
) {
    val bgColor by animateColorAsState(targetValue = if (selected) Color.Transparent else WhoppahPalette.Grey50, label = "tag_background")
    val borderColor by animateColorAsState(targetValue = if (selected) Color.Black else WhoppahPalette.Grey50, label = "tag_border")
    Row(
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        modifier = modifier
            .defaultMinSize(minHeight = 42.dp)
            .clip(shape = CircleShape)
            .background(color = bgColor, shape = CircleShape)
            .border(width = 1.dp, color = borderColor, shape = CircleShape)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 4.dp),
    ) {
        checkMark?.let {
            AnimatedContent(
                targetState = selected,
                label = "tag_checkmark",
                content = it,
            )
        }
        CompositionLocalProvider(LocalTextStyle provides WhoppahTheme.typography.h5) {
            content()
        }
    }
}

@Composable
fun WhColorTag(
    brush: Brush,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(6.dp, alignment = Alignment.Start),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    content: @Composable RowScope.() -> Unit,
) = WhTag(
    onClick = onClick,
    modifier = modifier,
    selected = selected,
    horizontalArrangement = horizontalArrangement,
    verticalAlignment = verticalAlignment,
    checkMark = WhTagDefaults.checkMarkWithColor(brush),
    content = content,
)

internal object WhTagDefaults {
    fun checkMark(): @Composable AnimatedVisibilityScope.(selected: Boolean) -> Unit {
        return {
            if (it)
                Icon(
                    imageVector = WhIcons.State.Checkmark, contentDescription = null,
                    tint = WhoppahPalette.AlertGreen, modifier = Modifier.size(24.dp),
                )
        }
    }

    fun checkMarkWithColor(brush: Brush): @Composable AnimatedVisibilityScope.(selected: Boolean) -> Unit {
        return {
            if (it)
                Icon(
                    imageVector = WhIcons.State.Checkmark, contentDescription = null,
                    tint = WhoppahPalette.AlertGreen, modifier = Modifier.size(24.dp),
                )
            else
                Canvas(
                    onDraw = { drawCircle(brush) },
                    modifier = Modifier.size(24.dp),
                )
        }
    }
}


//@Preview
//@Composable
//private fun Preview(
//    @PreviewParameter(BooleanPreviewParameterProvider::class) selected: Boolean
//) {
//    MaterialTheme {
//        WhTag(
//            onClick = {},
//            selected = selected,
//        ) { Text(text = "Andrew") }
//    }
//}
//
//@Preview
//@Composable
//private fun PreviewColorTag(
//    @PreviewParameter(BooleanPreviewParameterProvider::class) selected: Boolean
//) {
//    MaterialTheme {
//        WhColorTag(
//            brush = brushMultiColor,
//            onClick = {},
//            selected = selected,
//        ) { Text(text = "Andrew") }
//    }
//}

private val brushMultiColor: Brush = Brush.linearGradient(
    0.0f to Color.Yellow,
    0.25f to Color.Green,
    0.5f to Color.Blue,
    0.75f to Color.Red,
    start = Offset.Zero,
    end = Offset(20f, 20f),
    tileMode = TileMode.Repeated,
)