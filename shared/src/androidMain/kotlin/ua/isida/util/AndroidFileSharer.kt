package ua.isida.util

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Named
import java.io.File

/**
 * Android implementation of FileSharer using FileProvider.
 */
@Inject
class AndroidFileSharer(
    @param:Named("APPLICATION_CONTEXT") private val context: Context,
) : FileSharer {
    override fun shareFile(fileName: String, subFolder: String?, title: String) {
        val baseDir = if (subFolder != null) File(context.filesDir, subFolder) else context.filesDir
        val file = File(baseDir, fileName)
        if (!file.exists()) return

        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        val chooser = Intent.createChooser(intent, title).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(chooser)
    }
}
