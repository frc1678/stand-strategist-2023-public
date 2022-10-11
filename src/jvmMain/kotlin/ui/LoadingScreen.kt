package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.text.style.TextAlign
import files.readMatchSchedule
import files.readSettings
import files.readTeamData
import files.readTimData
import io.MAIN_FOLDER

@Composable
fun LoadingScreen(window: ComposeWindow, onLoaded: () -> Unit) {
    LaunchedEffect(true) {
        MAIN_FOLDER.mkdir()
        readSettings()
        readTimData()
        readTeamData()
        readMatchSchedule(window)
        onLoaded()
    }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text("Loading...", textAlign = TextAlign.Center)
    }
}
