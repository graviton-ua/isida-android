package com.whoppah.common.compose.ui

//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.whoppah.common.compose.icons.WhIcons
import com.whoppah.common.compose.icons.action.Edit
import com.whoppah.common.compose.icons.profile.ProfileCircle
import com.whoppah.common.compose.theme.WhoppahPalette

@Composable
fun WhProfileImage(
    model: Any?,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    border: BorderStroke? = null,
    contentColor: Color = WhoppahPalette.Grey300,
    backgroundColor: Color = WhoppahPalette.White,
    icon: ImageVector = WhIcons.Profile.ProfileCircle,
    onClick: (() -> Unit)? = null,
    onEdit: (() -> Unit)? = null,
) {
    val painter = rememberVectorPainter(image = icon)
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f),
    ) {
        AsyncImage(
            model = model,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .clip(shape = shape)
                .let { m -> border?.let { m.border(border = it, shape = shape) } ?: m }
                .background(color = backgroundColor, shape = shape)
                .let { m -> onClick?.let { m.clickable(onClick = it) } ?: m }
                .drawWithCache {
                    onDrawWithContent {
                        inset(
                            vertical = -size.height.times(0.2f) / 2,
                            horizontal = -size.width.times(0.2f) / 2,
                        ) {
                            with(painter) { draw(size = size, colorFilter = ColorFilter.tint(color = contentColor)) }
                        }
                        drawContent()
                    }
                },
        )

        if (onEdit != null)
            WhButton(
                onClick = onEdit,
                shape = CircleShape,
                colors = WhButtonDefaults.buttonColorsTertiaryInvert(),
                elevation = 1.dp,
                contentPadding = PaddingValues(4.dp),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(24.dp),
            ) { Icon(imageVector = WhIcons.Action.Edit, contentDescription = null) }
    }
}


//@Preview
//@Composable
//private fun Preview() {
//    WhoppahTheme {
//        WhProfileImage(
//            model = null,
//            onEdit = {},
//            modifier = Modifier.size(96.dp),
//        )
//    }
//}