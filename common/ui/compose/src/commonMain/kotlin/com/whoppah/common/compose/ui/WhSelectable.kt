package com.whoppah.common.compose.ui

//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.whoppah.common.compose.theme.WhoppahTheme

@Composable
fun WhSelectableColumn(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.spaceBetween(minSpace = 8.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
    colors: WhSelectableColors = WhSelectableDefaults.selectableColors(),
    shape: Shape = WhoppahTheme.shapes.medium,
    enabled: Boolean = true,
    defaultContentColor: Boolean = true,
    leadingElement: (@Composable ColumnScope.() -> Unit)? = null,
    trailingElement: (@Composable ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        modifier = modifier
            .clip(shape = shape)
            .clickable(enabled = enabled, onClick = onClick)
            .background(color = colors.backgroundColor(enabled = enabled, selected = isSelected).value, shape = shape)
            .border(width = 1.dp, color = colors.borderColor(enabled = enabled, selected = isSelected).value, shape = shape)
            .padding(contentPadding),
    ) {
        CompositionLocalProvider(
            LocalContentColor provides colors.leadingElementColor(enabled = enabled, selected = isSelected).value
        ) {
            leadingElement?.invoke(this)
        }

        CompositionLocalProvider(
            LocalContentColor provides if (defaultContentColor) colors.contentDefaultColor(enabled = enabled).value else
                colors.contentColor(enabled = enabled, selected = isSelected).value
        ) {
            content.invoke(this)
        }

        CompositionLocalProvider(
            LocalContentColor provides colors.trailingElementColor(enabled = enabled, selected = isSelected).value
        ) {
            trailingElement?.invoke(this)
        }
    }
}

@Composable
fun WhSelectableRow(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spaceBetween(minSpace = 8.dp),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
    colors: WhSelectableColors = WhSelectableDefaults.selectableColors(),
    shape: Shape = WhoppahTheme.shapes.medium,
    enabled: Boolean = true,
    hasBorder: Boolean = true,
    defaultContentColor: Boolean = true,
    leadingElement: (@Composable RowScope.() -> Unit)? = null,
    trailingElement: (@Composable RowScope.() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement,
        modifier = modifier
            .clip(shape = shape)
            .clickable(enabled = enabled, onClick = onClick)
            .background(color = colors.backgroundColor(enabled = enabled, selected = isSelected).value, shape = shape)
            .let {
                if (hasBorder)
                    it.border(width = 1.dp, color = colors.borderColor(enabled = enabled, selected = isSelected).value, shape = shape)
                else it
            }
            .padding(contentPadding),
    ) {
        CompositionLocalProvider(
            LocalContentColor provides colors.leadingElementColor(enabled = enabled, selected = isSelected).value
        ) {
            leadingElement?.invoke(this)
        }

        CompositionLocalProvider(
            LocalContentColor provides if (defaultContentColor) colors.contentDefaultColor(enabled = enabled).value else
                colors.contentColor(enabled = enabled, selected = isSelected).value
        ) {
            content.invoke(this)
        }

        CompositionLocalProvider(
            LocalContentColor provides colors.trailingElementColor(enabled = enabled, selected = isSelected).value
        ) {
            trailingElement?.invoke(this)
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//private fun PreviewColumn(
//    @PreviewParameter(BooleanPreviewParameterProvider::class) selected: Boolean,
//) {
//    WhoppahTheme {
//        WhSelectableColumn(
//            leadingElement = {
//                RadioButton(selected = selected, onClick = { /*TODO*/ }, modifier = Modifier.background(Color.Black))
//            },
//            trailingElement = {
//                Icon(
//                    imageVector = WhIcons.Delivery.ShippingBox,
//                    contentDescription = null,
//                    modifier = Modifier.background(Color.Black)
//                )
//            },
//            isSelected = selected,
//            onClick = {},
//            modifier = Modifier,
//        ) {
//            Column(
//                modifier = Modifier.background(Color.Green),
//            ) {
//                Text(text = "Some title")
//                Text(text = "Subtitle here")
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun PreviewRow(
//    @PreviewParameter(BooleanPreviewParameterProvider::class) selected: Boolean,
//) {
//    WhoppahTheme {
//        WhSelectableRow(
//            leadingElement = {
//                RadioButton(selected = selected, onClick = { /*TODO*/ }, modifier = Modifier.background(Color.Black))
//            },
//            trailingElement = {
//                Icon(
//                    imageVector = WhIcons.Delivery.ShippingBox,
//                    contentDescription = null,
//                    modifier = Modifier.background(Color.Black)
//                )
//            },
//            isSelected = selected,
//            onClick = {},
//            modifier = Modifier.fillMaxWidth(),
//        ) {
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//                    .background(Color.Green),
//            ) {
//                Text(text = "Some title")
//                Text(text = "Subtitle here")
//            }
//        }
//    }
//}