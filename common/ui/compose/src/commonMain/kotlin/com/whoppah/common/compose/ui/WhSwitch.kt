package com.whoppah.common.compose.ui

//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.whoppah.common.compose.theme.WhoppahTheme

@Composable
fun WhSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    thumbContent: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) = Switch(checked, onCheckedChange, modifier, thumbContent, enabled, colors, interactionSource)

@Composable
fun WhSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(16.dp),
    colors: SwitchColors = SwitchDefaults.colors(),
    contentPadding: PaddingValues = PaddingValues(),
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement,
        modifier = modifier
            .clip(shape = WhoppahTheme.shapes.small)
            .let { m -> onCheckedChange?.let { m.clickable(enabled = enabled) { onCheckedChange(!checked) } } ?: m }
            .padding(contentPadding),
    ) {
        WhSwitch(
            checked = checked,
            onCheckedChange = null,
            enabled = enabled,
            colors = colors,
        )

        content()
    }
}


//@Preview(showBackground = true)
//@Composable
//private fun Preview(
//    @PreviewParameter(BooleanPreviewParameterProvider::class) checked: Boolean,
//) {
//    WhoppahTheme {
//        WhSwitch(
//            checked = checked,
//            onCheckedChange = {},
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun PreviewWithContent(
//    @PreviewParameter(BooleanPreviewParameterProvider::class) checked: Boolean,
//) {
//    WhoppahTheme {
//        WhSwitch(
//            checked = checked,
//            onCheckedChange = {},
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) { Text(text = "Example with content") }
//    }
//}