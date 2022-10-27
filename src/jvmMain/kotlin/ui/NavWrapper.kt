package ui

import Screens
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import files.settings

@Composable
fun NavWrapper() {
    Row(modifier = Modifier.fillMaxSize()) {
        IconButton(onClick = {}, modifier = Modifier.align(Alignment.CenterVertically)) {
            Icon(Icons.Outlined.ArrowBack, "Back")
        }
        when (settings!!.screen) {
            Screens.STARTING -> StartingScreen(Modifier.weight(1f))
            Screens.DATA -> DataScreen(Modifier.weight(1f))
            Screens.NOTES -> NotesScreen(Modifier.weight(1f))
        }
        IconButton(onClick = {}, modifier = Modifier.align(Alignment.CenterVertically)) {
            Icon(Icons.Outlined.ArrowForward, "Forward")
        }
    }
}
