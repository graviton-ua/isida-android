package ua.graviton.isida.ui.navigation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import org.jetbrains.compose.resources.StringResource

@Immutable
interface HomeTabScreen : NavKey {
    val icon: ImageVector
    val title: StringResource
}