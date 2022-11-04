package ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.text.style.TextAlign
import io.files.readMatchSchedule
import io.files.readSettings
import io.files.readTeamData
import io.files.readTimData
import io.MAIN_FOLDER

@Composable
fun LoadingPage(window: ComposeWindow, onLoaded: () -> Unit) {
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
