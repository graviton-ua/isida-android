package com.whoppah.common.compose.ui

//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.whoppah.common.compose.theme.WhoppahTheme

@Composable
fun WhCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors(checkedColor = WhoppahTheme.colors.primary),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) = Checkbox(checked, onCheckedChange, modifier, enabled, colors, interactionSource)

@Composable
fun WhCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(16.dp),
    colors: CheckboxColors = CheckboxDefaults.colors(checkedColor = WhoppahTheme.colors.primary),
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
        WhCheckbox(
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
//        WhCheckbox(
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
//        WhCheckbox(
//            checked = checked,
//            onCheckedChange = {},
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) { Text(text = "Example with content") }
//    }
//}