package ui

import Screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.files.editSettings
import io.files.settings
import ui.pages.DataPage
import ui.pages.NotesPage
import ui.pages.StartingPage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavWrapper() {
    Column(modifier = Modifier.fillMaxSize().padding(50.dp)) {
        Box(modifier = Modifier.weight(1f)) {
            when (settings!!.screen) {
                Screens.STARTING -> StartingPage()
                Screens.DATA -> DataPage()
                Screens.NOTES -> NotesPage()
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(50.dp), modifier = Modifier.padding(horizontal = 150.dp)) {
            Card(modifier = Modifier.weight(1f), backgroundColor = MaterialTheme.colors.primarySurface, onClick = {
                editSettings {
                    if (screen == Screens.STARTING && match > 1) match--
                    screen = previousScreen(screen)
                }
            }) {
                Row(
                    modifier = Modifier.padding(30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Icon(Icons.Default.ArrowBack, "Back")
                    Column {
                        Text("Back")
                        Text(screenName(previousScreen(settings?.screen)))
                    }
                }
            }
            Card(modifier = Modifier.weight(1f), backgroundColor = MaterialTheme.colors.primarySurface, onClick = {
                editSettings {
                    if (screen == Screens.NOTES && match <= 200) match++
                    screen = previousScreen(screen)
                }
            }) {
                Row(
                    modifier = Modifier.padding(30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Icon(Icons.Default.ArrowForward, "Next")
                    Column {
                        Text("Next")
                        Text(screenName(nextScreen(settings?.screen)))
                    }
                }
            }
        }
    }
}

fun previousScreen(screen: Screens?) = when (screen) {
    Screens.STARTING -> Screens.NOTES
    Screens.DATA -> Screens.STARTING
    Screens.NOTES -> Screens.DATA
    else -> Screens.STARTING
}

fun nextScreen(screen: Screens?) = when (screen) {
    Screens.STARTING -> Screens.DATA
    Screens.DATA -> Screens.NOTES
    Screens.NOTES -> Screens.STARTING
    else -> Screens.STARTING
}

fun screenName(screen: Screens?) = when (screen) {
    Screens.STARTING -> "Match Info"
    Screens.DATA -> "Match Data"
    Screens.NOTES -> "Team Notes"
    else -> ""
}
