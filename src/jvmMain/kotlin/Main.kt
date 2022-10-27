import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.Observer
import ui.LoadingScreen
import ui.NavWrapper

@Composable
@Preview
fun App(window: ComposeWindow) {
    MaterialTheme {
        Observer()
        var loaded by remember { mutableStateOf(false) }
        if (!loaded) {
            LoadingScreen(window, onLoaded = { loaded = true })
        } else {
            NavWrapper()
        }
    }
}

enum class Screens {
    STARTING, DATA, NOTES
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Stand Strategist") {
        App(window)
    }
}
