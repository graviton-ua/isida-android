package ua.isida.common.ui.compose.ui

//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ua.isida.common.ui.compose.theme.IsidaTheme

@Composable
fun AppRadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: RadioButtonColors = RadioButtonDefaults.colors(selectedColor = IsidaTheme.colors.primary),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) = RadioButton(selected, onClick, modifier, enabled, colors, interactionSource)

@Composable
fun AppRadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(16.dp),
    colors: RadioButtonColors = RadioButtonDefaults.colors(selectedColor = IsidaTheme.colors.primary),
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement,
        modifier = modifier
            .clip(shape = IsidaTheme.shapes.small)
            .let { m -> onClick?.let { m.clickable(enabled = enabled, onClick = it) } ?: m }
            .padding(contentPadding),
    ) {
        AppRadioButton(
            selected = selected,
            onClick = null,
            enabled = enabled,
            colors = colors,
        )

                content()
            }
        }
        