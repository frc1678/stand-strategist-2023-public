package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import files.matchSchedule
import files.settings
import files.team
import files.teamData
import files.teamDataCols
import org.jetbrains.kotlinx.dataframe.api.firstOrNull
import org.jetbrains.kotlinx.dataframe.api.update
import org.jetbrains.kotlinx.dataframe.api.where
import org.jetbrains.kotlinx.dataframe.api.with

@Composable
fun NotesScreen(modifier: Modifier) {
    Row(modifier = modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly) {
        val teams = matchSchedule!![settings!!.match.toString()]?.teams?.filter {
            it.color == settings!!.alliance
        }
        Row(modifier = modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(
                modifier = Modifier.padding(horizontal = 10.dp).fillMaxHeight().padding(top = 90.dp),
                verticalArrangement = Arrangement.spacedBy(170.dp)
            ) {
                Text("")
                for (currentTeam in teams ?: emptyList()) {
                    Text("${currentTeam.number}")
                }
            }
            for (col in teamDataCols.filter { it.name() != team.name() }) {
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp).padding(top = 90.dp).fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(145.dp)
                ) {
                    Text(col.name())
                    for (currentTeam in teams ?: emptyList()) {
                        TeamDataText(
                            initialData = teamData!!
                                .firstOrNull { it[team] == currentTeam.number }
                                ?.get(col)
                                ?.toString() ?: "",
                            onChange = { new ->
                                teamData = teamData!!.update(col)
                                    .where { team() == currentTeam.number }
                                    .with { new }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TeamDataText(initialData: String, onChange: (String) -> Unit) {
    var text by remember { mutableStateOf(initialData) }
    TextField(
        value = text,
        onValueChange = {
            text = it
            onChange(it)
        },
        modifier = Modifier.width(330.dp)
    )
}
