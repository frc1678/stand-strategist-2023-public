package ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.files.alliance
import io.files.match
import io.files.matchSchedule
import io.files.settings
import io.files.team
import io.files.timData
import io.files.timDataCols
import org.jetbrains.kotlinx.dataframe.api.first
import org.jetbrains.kotlinx.dataframe.api.firstOrNull
import org.jetbrains.kotlinx.dataframe.api.update
import org.jetbrains.kotlinx.dataframe.api.where
import org.jetbrains.kotlinx.dataframe.api.with
import ui.CheckBox
import ui.NumberPicker
import ui.TextDataField

@Composable
fun DataPage() {
    val teams = matchSchedule!![settings!!.match.toString()]?.teams?.filter {
        it.color == settings!!.alliance
    }
    Row(modifier = Modifier.fillMaxSize().padding(vertical = 50.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth().weight(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.5f))
            for (currentTeam in teams ?: emptyList()) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text("${currentTeam.number}")
                }
            }
        }
        for (col in timDataCols.keys.filter { it.name() !in listOf(team.name(), alliance.name(), match.name()) }) {
            Column(modifier = Modifier.weight(1f).fillMaxHeight().padding(horizontal = 5.dp)) {
                Box(modifier = Modifier.weight(0.5f), contentAlignment = Alignment.Center) {
                    Text(col.name())
                }
                for (currentTeam in teams ?: emptyList()) {
                    when (timData!!.first()[col]) {
                        is Boolean -> {
                            CheckBox(
                                initialData = timData!!
                                    .firstOrNull {
                                        it[team] == currentTeam.number &&
                                            it[alliance] == currentTeam.color &&
                                            it[match] == settings!!.match
                                    }
                                    ?.get(col) as Boolean,
                                onChange = { new ->
                                    timData = timData!!.update(col)
                                        .where {
                                            team() == currentTeam.number &&
                                                alliance() == currentTeam.color &&
                                                match() == settings!!.match
                                        }
                                        .with { new }
                                },
                                modifier = Modifier.weight(1f).wrapContentHeight()
                            )
                        }

                        is Int -> {
                            NumberPicker(
                                initialData = timData!!
                                    .firstOrNull {
                                        it[team] == currentTeam.number &&
                                            it[alliance] == currentTeam.color &&
                                            it[match] == settings!!.match
                                    }
                                    ?.get(col) as Int,
                                onChange = { new ->
                                    timData = timData!!.update(col)
                                        .where {
                                            team() == currentTeam.number &&
                                                alliance() == currentTeam.color &&
                                                match() == settings!!.match
                                        }
                                        .with { new }
                                },
                                modifier = Modifier.weight(1f).wrapContentHeight()
                            )
                        }

                        else -> {
                            TextDataField(
                                initialData = timData!!
                                    .firstOrNull {
                                        it[team] == currentTeam.number &&
                                            it[alliance] == currentTeam.color &&
                                            it[match] == settings!!.match
                                    }
                                    ?.get(col)
                                    ?.toString() ?: "",
                                onChange = { new ->
                                    timData = timData!!.update(col)
                                        .where {
                                            team() == currentTeam.number &&
                                                alliance() == currentTeam.color &&
                                                match() == settings!!.match
                                        }
                                        .with { new }
                                },
                                modifier = Modifier.weight(1f).wrapContentHeight()
                            )
                        }
                    }
                }
            }
        }
    }
}
