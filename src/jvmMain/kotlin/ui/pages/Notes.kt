package ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun NotesPage() {
    val teams = matchSchedule!![settings!!.match.toString()]?.teams?.filter {
        it.color == settings!!.alliance
    }
    Row(modifier = Modifier.fillMaxSize().padding(vertical = 50.dp)) {
        Column(
            modifier = Modifier.fillMaxHeight().weight(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.5f))
            for (currentTeam in teams ?: emptyList()) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text("${currentTeam.number}")
                }
            }
        }
        for (col in teamDataCols.keys.filter { it.name() != team.name() }) {
            Column(modifier = Modifier.weight(1f).fillMaxHeight().padding(horizontal = 5.dp)) {
                Box(modifier = Modifier.weight(0.5f), contentAlignment = Alignment.Center) {
                    Text(col.name())
                }
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
                        modifier = Modifier.weight(1f).wrapContentHeight()
                    )
                }
            }
        }
    }
}
