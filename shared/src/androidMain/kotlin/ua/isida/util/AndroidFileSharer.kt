package ua.isida.util

import android.content.Context
import android.content.Intent
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import dev.zacsweers.metro.*
import kotlinx.io.files.Path
import java.io.File

/**
 * Android implementation of FileSharer using FileProvider.
 */
@Inject @ContributesBinding(scope = AppScope::class, binding = binding<FileSharer>())
class AndroidFileSharer(
    @param:Named("APPLICATION_CONTEXT") private val context: Context,
) : FileSharer {

    private fun getMimeType(file: File): String {
        val extension = file.extension
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: "text/plain"
    }

    override fun shareFile(filePath: Path, title: String) {
        val file = File(filePath.toString())
        if (!file.exists()) return

        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        val mimeType = getMimeType(file)

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = mimeType
            putExtra(Intent.EXTRA_STREAM, uri)
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        val chooser = Intent.createChooser(intent, title).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(chooser)
    }

    override fun shareFiles(filePaths: List<Path>, title: String) {
        if (filePaths.isEmpty()) return
        if (filePaths.size == 1) {
            shareFile(filePaths[0], title)
            return
        }

        val uris = ArrayList(filePaths.mapNotNull { path ->
            val file = File(path.toString())
            if (file.exists()) {
                FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
            } else null
        })

        if (uris.isEmpty()) return

        // For multiple files, if they are all logs, "text/plain" is the safest common type
        val intent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
            type = "text/plain"
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        val chooser = Intent.createChooser(intent, title).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(chooser)
    }
}
