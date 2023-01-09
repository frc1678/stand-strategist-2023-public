package ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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

/**
 * The 'Back' and 'Next' navigation buttons that appear at the bottom of the app UI.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavButtons() {
    Row(horizontalArrangement = Arrangement.spacedBy(50.dp), modifier = Modifier.padding(horizontal = 150.dp)) {
        // 'Back' button
        Card(modifier = Modifier.weight(1f), backgroundColor = MaterialTheme.colors.primarySurface, onClick = {
            editSettings {
                screen.apply { onBack() }
                screen = screen.back().destination
            }
        }) {
            Row(
                modifier = Modifier.padding(30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Icon(Icons.Default.ArrowBack, "Back")
                Column {
                    Text("Back", style = MaterialTheme.typography.h6)
                    Text(
                        settings?.screen?.back?.let { it() }?.destination?.name ?: "",
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }

        // 'Next' button (only appears if there is a specified next page)
        if (settings!!.screen.next() != null) {
            Card(modifier = Modifier.weight(1f), backgroundColor = MaterialTheme.colors.primarySurface, onClick = {
                editSettings {
                    screen.apply { onNext() }
                    screen = screen.next()!!.destination
                }
            }) {
                Row(
                    modifier = Modifier.padding(30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Icon(Icons.Default.ArrowForward, "Next")
                    Column {
                        Text("Next", style = MaterialTheme.typography.h6)
                        Text(
                            settings?.screen?.next?.let { it() }?.destination?.name ?: "",
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
        }
    }
}
