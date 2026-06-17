package ua.isida.common.ui.compose.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.isida.common.ui.compose.theme.IsidaTheme

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevation: Dp? = null,
    shape: Shape = IsidaTheme.shapes.small,
    border: BorderStroke? = null,
    colors: ButtonColors = AppButtonDefaults.buttonColors(),
    size: ButtonSize = ButtonSize.Medium,
    textStyle: TextStyle = IsidaTheme.typography.button,
    contentPadding: PaddingValues? = null,
    content: @Composable RowScope.() -> Unit
) = Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    elevation = elevation,
    shape = shape,
    border = border,
    size = size,
    textStyle = textStyle,
    colors = colors,
    contentPadding = contentPadding ?: size.contentPadding,
    content = content,
)

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevation: Dp? = null,
    shape: Shape = IsidaTheme.shapes.small,
    border: BorderStroke? = null,
    colors: AppBrushButtonColors,
    size: ButtonSize = ButtonSize.Medium,
    textStyle: TextStyle = IsidaTheme.typography.button,
    contentPadding: PaddingValues? = null,
    content: @Composable RowScope.() -> Unit
) = Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    elevation = elevation,
    shape = shape,
    border = border,
    size = size,
    textStyle = textStyle,
    colors = colors,
    contentPadding = contentPadding ?: size.contentPadding,
    content = content,
)

@Composable
fun AppOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = IsidaTheme.shapes.small,
    colors: ButtonColors = AppButtonDefaults.outlinedButtonColors(),
    elevation: Dp? = null,
    border: BorderStroke? = BorderStroke(1.dp, colors.contentColor(enabled = enabled)),
    size: ButtonSize = ButtonSize.Medium,
    textStyle: TextStyle = IsidaTheme.typography.button,
    contentPadding: PaddingValues? = null,
    content: @Composable RowScope.() -> Unit
) = Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    elevation = elevation,
    shape = shape,
    border = border,
    size = size,
    textStyle = textStyle,
    colors = colors,
    contentPadding = contentPadding ?: size.contentPadding,
    content = content,
)

@Composable
fun AppTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevation: Dp? = null,
    shape: Shape = IsidaTheme.shapes.small,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    border: BorderStroke? = null,
    size: ButtonSize = ButtonSize.Medium,
    textStyle: TextStyle = IsidaTheme.typography.button,
    contentPadding: PaddingValues? = null,
    content: @Composable RowScope.() -> Unit
) = Button(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    elevation = elevation,
    shape = shape,
    border = border,
    size = size,
    textStyle = textStyle,
    colors = colors,
    contentPadding = contentPadding ?: size.contentPadding,
    content = content,
)

@Composable
fun TopBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) = IconButton(onClick = onClick, modifier = modifier) {
    Icon(
        imageVector = Icons.Default.ChevronLeft,
        contentDescription = "Back",
        tint = LocalContentColor.current
    )
}


@Composable
private fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.small,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: Dp? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    size: ButtonSize? = null,
    textStyle: TextStyle = IsidaTheme.typography.button,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val containerColor = colors.containerColor(enabled)
    val contentColor = colors.contentColor(enabled)
    Box(
        modifier = modifier
            .semantics { role = Role.Button }
            //.minimumInteractiveComponentSize()
            .surface(
                shape = shape,
                backgroundColor = containerColor,
                border = border,
                elevation = elevation ?: 0.dp,
            )
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(),
                enabled = enabled,
                onClick = onClick
            ),
        propagateMinConstraints = true
    ) {
        val _textStyle = size?.contentFontSize?.let { textStyle.copy(fontSize = it) } ?: textStyle
        val mergedStyle = LocalTextStyle.current.merge(_textStyle)
        CompositionLocalProvider(
            LocalContentColor provides contentColor.copy(alpha = 1f),
            LocalTextStyle provides mergedStyle,
        ) {
            Row(
                modifier = Modifier
                    .defaultMinSize(
                        minWidth = size?.minWidth ?: ButtonDefaults.MinWidth,
                        minHeight = size?.minHeight ?: ButtonDefaults.MinHeight
                    )
                    .padding(contentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}

@Composable
private fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: Dp? = null,
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    size: ButtonSize? = null,
    textStyle: TextStyle = IsidaTheme.typography.button,
    colors: AppBrushButtonColors,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    val containerColor by colors.backgroundBrush(enabled)
    val contentColor by colors.contentColor(enabled)
    Box(
        modifier = modifier
            .semantics { role = Role.Button }
            //.minimumInteractiveComponentSize()
            .surface(
                shape = shape,
                backgroundBrush = containerColor,
                border = border,
                elevation = elevation ?: 0.dp,
            )
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(),
                enabled = enabled,
                onClick = onClick
            ),
        propagateMinConstraints = true
    ) {
        val _textStyle = size?.contentFontSize?.let { textStyle.copy(fontSize = it) } ?: textStyle
        val mergedStyle = LocalTextStyle.current.merge(_textStyle)
        CompositionLocalProvider(
            LocalContentColor provides contentColor.copy(alpha = 1f),
            LocalTextStyle provides mergedStyle,
        ) {
            Row(
                modifier = Modifier
                    .defaultMinSize(
                        minWidth = size?.minWidth ?: ButtonDefaults.MinWidth,
                        minHeight = size?.minHeight ?: ButtonDefaults.MinHeight
                    )
                    .padding(contentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}


@Immutable
class AppBrushButtonColors(
    private val containerBrush: Brush,
    private val contentColor: Color,
    private val disabledContainerBrush: Brush,
    private val disabledContentColor: Color
) {
    @Composable
    fun backgroundBrush(enabled: Boolean): State<Brush> {
        return rememberUpdatedState(if (enabled) containerBrush else disabledContainerBrush)
    }

    @Composable
    fun contentColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) contentColor else disabledContentColor)
    }

    fun copy(
        containerBrush: Brush? = this.containerBrush,
        contentColor: Color = this.contentColor,
        disabledContainerBrush: Brush? = this.disabledContainerBrush,
        disabledContentColor: Color = this.disabledContentColor,
    ) =
        AppBrushButtonColors(
            containerBrush ?: this.containerBrush,
            contentColor.takeOrElse { this.contentColor },
            disabledContainerBrush ?: this.disabledContainerBrush,
            disabledContentColor.takeOrElse { this.disabledContentColor },
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as AppBrushButtonColors

        if (containerBrush != other.containerBrush) return false
        if (contentColor != other.contentColor) return false
        if (disabledContainerBrush != other.disabledContainerBrush) return false
        if (disabledContentColor != other.disabledContentColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = containerBrush.hashCode()
        result = 31 * result + contentColor.hashCode()
        result = 31 * result + disabledContainerBrush.hashCode()
        result = 31 * result + disabledContentColor.hashCode()
        return result
    }
}


sealed class ButtonSize(
    val minWidth: Dp = ButtonDefaults.MinWidth,
    val minHeight: Dp = ButtonDefaults.MinHeight,
    val contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    val contentFontSize: TextUnit = 16.sp,
) {
    data object Small : ButtonSize(
        minHeight = 30.dp,
        contentPadding = PaddingValues(vertical = 6.dp, horizontal = 16.dp),
        contentFontSize = 14.sp,
    )

    data object Medium36 : ButtonSize(
        minHeight = 36.dp,
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        contentFontSize = 16.sp,
    )

    data object Medium : ButtonSize(
        minHeight = 44.dp,
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        contentFontSize = 16.sp,
    )

    data object Large : ButtonSize(
        minHeight = 48.dp,
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        contentFontSize = 16.sp,
    )

    data object CircleSmall : ButtonSize(
        minWidth = 30.dp, minHeight = 30.dp,
        contentPadding = PaddingValues(vertical = 6.dp, horizontal = 6.dp),
        contentFontSize = 14.sp,
    )
}


private fun Modifier.surface(
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke?,
    elevation: Dp
) = this
    .shadow(elevation, shape, clip = false)
    .then(if (border != null) Modifier.border(border, shape) else Modifier)
    .background(color = backgroundColor, shape = shape)
    .clip(shape)

private fun Modifier.surface(
    shape: Shape,
    backgroundBrush: Brush,
    border: BorderStroke?,
    elevation: Dp
) = this
    .shadow(elevation, shape, clip = false)
    .then(if (border != null) Modifier.border(border, shape) else Modifier)
    .background(brush = backgroundBrush, shape = shape)
    .clip(shape)


@Stable
private fun ButtonColors.containerColor(enabled: Boolean): Color = if (enabled) containerColor else disabledContainerColor

@Stable
private fun ButtonColors.contentColor(enabled: Boolean): Color = if (enabled) contentColor else disabledContentColor