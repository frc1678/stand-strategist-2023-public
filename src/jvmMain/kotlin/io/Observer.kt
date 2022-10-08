package io

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import files.settings
import files.settingsWriter
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
