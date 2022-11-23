import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.isMetaPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.Observer
import io.files.editSettings
import io.files.settings
import io.saveDialog
import ui.NavWrapper
import ui.pages.LoadingPage
import ui.theme.StandStrategistDarkColorScheme
import ui.theme.StandStrategistLightColorScheme
import ui.theme.StandStrategistTypography

@Composable
@Preview
fun App(window: ComposeWindow) {
    MaterialTheme(
        typography = StandStrategistTypography,
        colors = if (settings?.darkTheme != false) StandStrategistDarkColorScheme else StandStrategistLightColorScheme
    ) {
        Scaffold(
            topBar = {
                TopAppBar {
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = { editSettings { darkTheme = !darkTheme } }) {
                        Icon(
                            painter = if (settings?.darkTheme != false) {
                                painterResource(
                                    "drawable/dark_mode_black_24dp.svg"
                                )
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
        ) {
            Observer()
            var loaded by remember { mutableStateOf(false) }
            if (!loaded) {
                LoadingPage(window, onLoaded = { loaded = true })
            } else {
                NavWrapper()
            }
        }
    }
}

enum class Screens {
    STARTING, DATA, NOTES
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Stand Strategist", onKeyEvent = {
        if ((it.isMetaPressed || it.isCtrlPressed) && it.key == Key.S && it.type == KeyEventType.KeyDown) {
            saveDialog(composeWindow!!)
        }
        true
    }) {
        LaunchedEffect(true) {
            composeWindow = window
        }
        App(window)
    }
}

var composeWindow: ComposeWindow? = null
