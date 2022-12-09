package ui

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
import io.files.editSettings
import io.files.settings
import io.saveDialog

@Composable
fun TopBar(window: ComposeWindow) = TopAppBar {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("Match Number: ${settings?.match}", modifier = Modifier.align(Alignment.Center))
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
