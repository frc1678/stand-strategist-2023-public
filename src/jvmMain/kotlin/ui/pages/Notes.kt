package ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.files.matchSchedule
import io.files.settings
import io.files.team
import io.files.teamData
import io.files.teamDataCols
import org.jetbrains.kotlinx.dataframe.api.firstOrNull
import org.jetbrains.kotlinx.dataframe.api.update
import org.jetbrains.kotlinx.dataframe.api.where
import org.jetbrains.kotlinx.dataframe.api.with
import ui.TextDataField

@Composable
fun NotesPage(modifier: Modifier) {
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
                    TextDataField(
                        initialData = teamData!!
                            .firstOrNull { it[team] == currentTeam.number }
                            ?.get(col)
                            ?.toString() ?: "",
                        onChange = { new ->
                            teamData = teamData!!.update(col)
                                .where { team() == currentTeam.number }
                                .with { new }
                        },
                        modifier = Modifier.width(330.dp)
                    )
                }
            }
        }
    }
}
