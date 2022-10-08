package files

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun Observer() {
    LaunchedEffect(settings) {
        if (settings != null) settingsWriter.writeData(settings!!)
    }
}
