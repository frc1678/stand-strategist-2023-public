package ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.files.Match
import io.files.Team
import io.files.editSettings
import io.files.matchSchedule
import io.files.populateTeamData
import io.files.populateTimData
import ui.TextDataField
import ui.navigation.NavGraph
import ui.navigation.navigateTo
import ui.theme.CustomTypography

@Composable
fun NewMatchPage() {
    var matchNumber by remember { mutableStateOf("") }
    var blueTeams by remember { mutableStateOf(listOf("", "", "")) }
    var redTeams by remember { mutableStateOf(listOf("", "", "")) }
    Column(modifier = Modifier.padding(50.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(50.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Match Number:", style = CustomTypography.h5)
            TextDataField(initialData = "", onChange = { matchNumber = it })
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.weight(1f).fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxHeight()) {
                Text("Blue Teams", style = CustomTypography.h4)
                for (i in 0..2) {
                    TextDataField(
                        initialData = "",
                        onChange = { blueTeams = blueTeams.toMutableList().apply { set(i, it) } }
                    )
                }
            }
            Column(verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxHeight()) {
                Text("Red Teams", style = CustomTypography.h4)
                for (i in 0..2) {
                    TextDataField(
                        initialData = "",
                        onChange = { redTeams = redTeams.toMutableList().apply { set(i, it) } }
                    )
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(50.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { navigateTo(NavGraph.MATCH_SELECTION) }, colors = ButtonDefaults.outlinedButtonColors()) {
                Text("Cancel")
            }
            Button(
                enabled = matchNumber != "" && !matchSchedule!!.containsKey(matchNumber) &&
                    blueTeams.toSet().size == blueTeams.size && redTeams.toSet().size == redTeams.size,
                onClick = {
                    matchSchedule = matchSchedule!!.toMutableMap().apply {
                        set(
                            matchNumber,
                            Match(teams = blueTeams.map { Team(number = it, color = "blue") } +
                                redTeams.map { Team(number = it, color = "red") }
                            )
                        )
                    }
                    editSettings { match = matchNumber }
                    populateTimData()
                    populateTeamData()
                    navigateTo(NavGraph.MATCH_SELECTION)
                }
            ) {
                Text("Done")
            }
        }
    }
}
