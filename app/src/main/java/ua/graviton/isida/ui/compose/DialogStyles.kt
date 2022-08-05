package ua.graviton.isida.ui.compose

import androidx.compose.ui.window.DialogProperties
import com.ramcosta.composedestinations.spec.DestinationStyle

object FreeDialogStyle : DestinationStyle.Dialog {
    override val properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false)
}