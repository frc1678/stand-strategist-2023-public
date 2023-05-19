package ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import io.MAIN_FOLDER
import io.MATCH_SCHEDULE_FILE
import io.files.matchSchedule
import io.files.populateTeamData
import io.files.populateTimData
import io.files.readMatchSchedule
import io.files.readSettings
import io.files.readTeamData
import io.files.readTimData
import ui.Dialog

/**
 * The page shown when loading all the data for the app. This page is not part of the navigation graph, since the
 * navigation graph is not loaded yet at this point. This page serves as a placeholder before all the files have been
 * read and the app is ready to be used.
 */
@Composable
fun WindowScope.LoadingPage(window: ComposeWindow, onLoaded: () -> Unit) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text("Loading...", textAlign = TextAlign.Center)
    }
    LaunchedEffect(true) { MAIN_FOLDER.mkdir() }
    fun loadOthers() {
        readSettings()
        readTimData()
        readTeamData()
        populateTeamData()
        populateTimData()
        onLoaded()
    }
    if (!MATCH_SCHEDULE_FILE.exists()) {
        Dialog(allowCancel = false) {
            Column(verticalArrangement = Arrangement.spacedBy(30.dp)) {
                Text("Choose match schedule...")
                Button(onClick = {
                    readMatchSchedule(window)
                    if (matchSchedule != null) loadOthers()
                }) { Text("Choose") }
            }
        }
    } else {
        LaunchedEffect(true) {
            readMatchSchedule(window)
            loadOthers()
        }
    }
}
