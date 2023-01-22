import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import io.Observer
import io.files.settings
import io.saveDialog
import ui.AnimatedContentByScreen
import ui.TopBar
import ui.navigation.NavButtons
import ui.pages.LoadingPage
import ui.theme.StandStrategistDarkColorScheme
import ui.theme.StandStrategistLightColorScheme
import ui.theme.StandStrategistTypography

@Composable
fun WindowScope.App(applicationScope: ApplicationScope, window: ComposeWindow) = MaterialTheme(
    typography = StandStrategistTypography,
    colors = if (settings?.darkTheme != false) StandStrategistDarkColorScheme else StandStrategistLightColorScheme
) {
    Surface(color = MaterialTheme.colors.background) {
        var loaded by remember { mutableStateOf(false) }
        if (!loaded) {
            LoadingPage(window, onLoaded = { loaded = true })
        } else {
            Scaffold(topBar = { applicationScope.TopBar(window) }) {
                Observer()
                Column(modifier = Modifier.fillMaxSize().padding(vertical = 50.dp)) {
                    AnimatedContentByScreen(modifier = Modifier.weight(1f)) { screen ->
                        Box(modifier = Modifier.padding(horizontal = 50.dp)) {
                            screen.content()
                        }
                    }
                    NavButtons()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    Window(
        state = WindowState(placement = WindowPlacement.Maximized),
        onCloseRequest = ::exitApplication,
        title = "Stand Strategist",
        onKeyEvent = {
            if ((it.isMetaPressed || it.isCtrlPressed) && it.key == Key.S && it.type == KeyEventType.KeyDown) {
                saveDialog(composeWindow!!)
            }
            true
        }
    ) {
        LaunchedEffect(true) {
            composeWindow = window
        }
        App(this@application, window)
    }
}

var composeWindow: ComposeWindow? = null
