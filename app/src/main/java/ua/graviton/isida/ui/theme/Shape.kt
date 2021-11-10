package ua.graviton.isida.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.unit.dp

val IsidaShapes = Shapes(
    /**
     * Shape used by small components like [Button] or [Snackbar]. Components like
     * [FloatingActionButton], [ExtendedFloatingActionButton] use this shape, but override
     * the corner size to be 50%. [TextField] uses this shape with overriding the bottom corners
     * to zero.
     */
    small = RoundedCornerShape(8.dp),
    /**
     * Shape used by medium components like [Card] or [AlertDialog].
     */
    medium = RoundedCornerShape(8.dp),
    /**
     * Shape used by large components like [ModalDrawer] or [ModalBottomSheetLayout].
     */
    large = RoundedCornerShape(0.dp)
)