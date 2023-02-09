package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import io.BACKUPS_FOLDER
import io.MATCH_SCHEDULE_FILE
import io.SETTINGS_FILE
import io.TEAM_DATA_FILE
import io.TIM_DATA_FILE
import io.files.editSettings
import io.files.settings
import io.saveDialog
import kotlinx.datetime.Clock
import ui.theme.CustomTypography
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

@Composable
fun Separator() = Text("\u22C5")

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ApplicationScope.TopBar(window: ComposeWindow) = TopAppBar {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.align(Alignment.Center)) {
            Text(settings?.screen?.name ?: "")
            Separator()
            Text("Match ${settings?.match ?: '?'}")
            Separator()
            Text(
                when (settings?.alliance) {
                    "red" -> "Red Alliance"
                    "blue" -> "Blue Alliance"
                    else -> "None"
                },
                style = CustomTypography.body1.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.onClick { editSettings { alliance = if (alliance == "red") "blue" else "red" } }
            )
        }
        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            var confirmingDeletion by remember { mutableStateOf(false) }
            if (!confirmingDeletion) {
                IconButton(onClick = { confirmingDeletion = true }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete data")
                }
            } else {
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    TextButton(onClick = {
                        BACKUPS_FOLDER.mkdir()
                        val backupFile = File(BACKUPS_FOLDER, "${Clock.System.now().toEpochMilliseconds()}.zip")
                        backupFile.createNewFile()
                        val stream = ZipOutputStream(backupFile.outputStream())
                        for (file in listOf(MATCH_SCHEDULE_FILE, TEAM_DATA_FILE, TIM_DATA_FILE, SETTINGS_FILE)) {
                            stream.putNextEntry(ZipEntry(file.name))
                            stream.write(file.readBytes())
                            stream.closeEntry()
                            file.deleteOnExit()
                        }
                        stream.close()
                        exitApplication()
                    }) {
                        Text("Confirm", style = CustomTypography.body1, color = MaterialTheme.colors.onSurface)
                    }
                    TextButton(onClick = { confirmingDeletion = false }) {
                        Text("Cancel", style = CustomTypography.body1, color = MaterialTheme.colors.onSurface)
                    }
                }
            }
            IconButton(onClick = { editSettings { darkTheme = !darkTheme } }) {
                Icon(
                    painter = if (settings?.darkTheme != false) {
                        painterResource("drawable/dark_mode_black_24dp.svg")
                    } else {
                        painterResource("drawable/light_mode_black_24dp.svg")
                    },
                    contentDescription = "Change theme"
                )
            }
            IconButton(onClick = { saveDialog(window) }) {
                Icon(
                    painter = painterResource("drawable/file_download_black_24dp.svg"),
                    contentDescription = "Export"
                )
            }
        }
    }
}
