package files

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Observer() {
    LaunchedEffect(true) {
        launch(Dispatchers.IO) {
            settingsWriter.start()
        }
    }
    LaunchedEffect(settings) {
        if (settings != null) settingsWriter.writeData(settings!!)
    }
}
