package ui.pages

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.files.matchSchedule
import io.files.settings
import io.files.team
import io.files.teamData
import io.files.teamDataCols
import ui.TextDataField
import ui.navigation.NavGraph
import ui.navigation.navigateTo
import ui.theme.CustomTypography

/**
 * The page showing the team notes for the teams in the current alliance in the current match. These are for team notes
 * **across the entire competition**, not match-specific notes.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NotesPage() = AnimatedContent(targetState = settings) { settings ->
    val teams = matchSchedule!![settings!!.match]?.teams?.filter {
        it.color == settings.alliance
    }
    Column(modifier = Modifier.fillMaxSize().padding(top = 50.dp, bottom = 40.dp)) {
        Row(modifier = Modifier.fillMaxSize().weight(1f)) {
            Column(
                modifier = Modifier.fillMaxHeight().weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(0.5f))
                for (currentTeam in teams ?: emptyList()) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(currentTeam.number, style = CustomTypography.h3)
                    }
                }
            }
            for (col in teamDataCols.keys.filter { it.name != team }) {
                Column(modifier = Modifier.weight(1f).fillMaxHeight().padding(horizontal = 5.dp)) {
                    Box(modifier = Modifier.weight(0.5f), contentAlignment = Alignment.Center) {
                        Text(col.name, style = CustomTypography.h4)
                    }
                    for (currentTeam in teams ?: emptyList()) {
                        TextDataField(
                            initialData = teamData!!.firstOrNull { it[team] == currentTeam.number }?.get(col.name)
                                ?: "",
                            onChange = { new ->
                                teamData = teamData!!.map {
                                    if (it[team] == currentTeam.number) {
                                        it.toMutableMap().apply { set(col.name, new) }
                                    } else {
                                        it
                                    }
                                }
                            },
                            modifier = Modifier.weight(1f).wrapContentHeight().fillMaxWidth()
                        )
                    }
                }
            }
        }
        Button(
            onClick = { navigateTo(NavGraph.ALL_NOTES) },
            contentPadding = PaddingValues(horizontal = 30.dp, vertical = 20.dp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("See all team notes")
            Spacer(modifier = Modifier.width(20.dp))
            Icon(Icons.Default.ArrowForward, null)
        }
    }
}
