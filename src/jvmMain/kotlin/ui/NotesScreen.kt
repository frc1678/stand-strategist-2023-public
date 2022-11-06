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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import files.editTeamData
import files.matchSchedule
import files.settings
import files.team
import files.teamData
import files.teamDataCols
import org.jetbrains.kotlinx.dataframe.api.single
import org.jetbrains.kotlinx.dataframe.api.singleOrNull
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
                for (i in 0 until 3) {
                    Text(teams?.get(i)?.number?.toString() ?: "?")
                }
            }
            for (col in teamDataCols.filter { it.name() != team.name() }) {
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp).padding(top = 90.dp).fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(145.dp)
                ) {
                    Text(col.name())
                    for (i in 0 until 3) {
                        val row = teamData!!.singleOrNull {
                            get(team).toString() == (teams?.get(i)?.number?.toString() ?: "")
                        }

                        TextField(
                            value = row?.get(col)?.toString() ?: "",
                            onValueChange = { input ->
                                editTeamData { update { col }.where { index() == row?.index() }.with { input } }
                            },
                            modifier = Modifier.width(330.dp)
                        )
                    }
                }
            }
        }
        /*Column(
            modifier = Modifier.padding(horizontal = 10.dp).padding(top = 90.dp).fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(145.dp)
        ) {
            Text("Notes")
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.width(330.dp)
            )
            TextField(
                value = text2,
                onValueChange = { text2 = it },
                modifier = Modifier.width(330.dp)
            )
            TextField(
                value = text3,
                onValueChange = { text3 = it },
                modifier = Modifier.width(330.dp)
            )
        }*/
    }
}
