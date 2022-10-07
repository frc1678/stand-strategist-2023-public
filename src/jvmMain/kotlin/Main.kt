import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.DataScreen
import ui.LoadingScreen
import ui.NotesScreen
import ui.StartingScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf(Screens.LOADING) }
        when (currentScreen) {
            Screens.LOADING -> LoadingScreen()
            Screens.STARTING -> StartingScreen()
            Screens.DATA -> DataScreen()
            Screens.NOTES -> NotesScreen()
        }
    }
}

enum class Screens {
    LOADING, STARTING, DATA, NOTES
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
