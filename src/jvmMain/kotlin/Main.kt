import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isMetaPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.Observer
import ui.pages.LoadingPage
import ui.NavWrapper

@Composable
@Preview
fun App(window: ComposeWindow) {
    MaterialTheme {
        Observer()
        var loaded by remember { mutableStateOf(false) }
        if (!loaded) {
            LoadingPage(window, onLoaded = { loaded = true })
        } else {
            NavWrapper()
        }
    }
}

enum class Screens {
    STARTING, DATA, NOTES
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Stand Strategist", onKeyEvent = {
        if (it.isMetaPressed && it.key == Key.S && it.type == KeyEventType.KeyDown) {
            println("yes")
        }
        true
    }) {
        App(window)
    }

}
