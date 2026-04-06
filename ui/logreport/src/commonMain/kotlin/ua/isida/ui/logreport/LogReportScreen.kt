package ua.isida.ui.logreport

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metrox.viewmodel.metroViewModel
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import ua.isida.common.ui.compose.ui.WhDialog
import ua.isida.common.ui.resources.Res
import ua.isida.common.ui.resources.btn_cancel
import ua.isida.common.ui.resources.btn_share_logs

@Serializable
data object LogReportScreen : NavKey

@Composable
fun LogReportScreen(
    onBack: () -> Unit,
    viewModel: LogReportViewModel = metroViewModel(),
) {
    val files by viewModel.files.collectAsStateWithLifecycle()

    WhDialog {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(Res.string.btn_share_logs),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            if (files.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No log files found")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(files) { fileInfo ->
                        LogFileItem(
                            info = fileInfo,
                            onToggle = { viewModel.toggleSelection(fileInfo) }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = stringResource(Res.string.btn_cancel))
                }
                Button(
                    onClick = {
                        viewModel.shareSelectedFiles()
                        onBack()
                    },
                    enabled = files.any { it.isSelected },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Share")
                }
            }
        }
    }
}

@Composable
private fun LogFileItem(
    info: LogFileInfo,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = info.isSelected,
            onCheckedChange = null
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = info.name,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = info.sizeFormatted,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
