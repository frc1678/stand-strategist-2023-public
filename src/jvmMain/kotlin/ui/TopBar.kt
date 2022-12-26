package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.files.editSettings
import io.files.settings
import io.saveDialog

@Composable
fun Separator() = Text("\u22C5")

@Composable
fun TopBar(window: ComposeWindow) = TopAppBar {
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
                }
            )
        }
        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
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
