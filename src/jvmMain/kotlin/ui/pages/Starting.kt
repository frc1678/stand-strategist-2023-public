package ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import io.files.editSettings
import io.files.matchSchedule
import io.files.settings
import org.jetbrains.skiko.PredefinedCursors
import ui.navigation.NavGraph
import ui.navigation.navigateTo
import ui.theme.CustomTypography
import ui.theme.blueAlliance
import ui.theme.onBlueAlliance
import ui.theme.onRedAlliance
import ui.theme.redAlliance

/**
 * The page in which users can edit the username, which match is being played, and which alliance is being scouted.
 */
@Composable
fun StartingPage() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = settings!!.name,
            onValueChange = { editSettings { name = it } },
            label = { Text("Your Name:") },
            textStyle = CustomTypography.h2,
            modifier = Modifier.padding(20.dp)
        )

        var matchNumText by remember { mutableStateOf(settings!!.match) }
        TextField(
            value = matchNumText,
            onValueChange = {
                matchNumText = it
                if (matchSchedule!!.containsKey(matchNumText)) editSettings { match = it }
            },
            label = { Text("Match Number: ") },
            textStyle = CustomTypography.h2,
            isError = !matchSchedule!!.containsKey(matchNumText),
            trailingIcon = {
                Button(
                    onClick = { navigateTo(NavGraph.MATCH_SELECTION) },
                    modifier = Modifier.padding(20.dp).pointerHoverIcon(PointerIcon(PredefinedCursors.HAND))
                ) {
                    Text("Choose...")
                }
            },
            maxLines = 1,
            modifier = Modifier.padding(20.dp)
        )

        Button(
            modifier = Modifier.padding(20.dp),
            contentPadding = PaddingValues(horizontal = 100.dp, vertical = 20.dp),
            onClick = {
                editSettings {
                    if (alliance == "blue") {
                        alliance = "red"
                    } else if (alliance == "red") alliance = "blue"
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = when (settings!!.alliance) {
                    "blue" -> MaterialTheme.colors.blueAlliance
                    "red" -> MaterialTheme.colors.redAlliance
                    else -> MaterialTheme.colors.surface
                },
                contentColor = when (settings!!.alliance) {
                    "blue" -> MaterialTheme.colors.onBlueAlliance
                    "red" -> MaterialTheme.colors.onRedAlliance
                    else -> MaterialTheme.colors.onSurface
                }
            )
        ) {
            Text(
                when (settings!!.alliance) {
                    "red" -> "Red Alliance"
                    "blue" -> "Blue Alliance"
                    else -> "None"
                }
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(50.dp)) {
            for (i in 0 until 3) {
                Text(
                    matchSchedule!![settings!!.match]?.teams?.filter {
                        it.color == settings!!.alliance
                    }?.getOrNull(i)?.number ?: "?",
                    style = CustomTypography.h2
                )
            }
        }
    }
}
