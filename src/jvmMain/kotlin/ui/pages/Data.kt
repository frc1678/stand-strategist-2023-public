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
import ui.theme.CustomTypography

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
                    Text(currentTeam.number, style = CustomTypography.h3)
                }
            }
        }
        for (col in timDataCols.keys.filter { it.name() !in listOf(team.name(), alliance.name(), match.name()) }) {
            val firstCell = timData!!.first()[col]
            Column(
                modifier = Modifier.weight(
                    if (firstCell is Boolean || firstCell is Int) 0.5f else 1f
                ).fillMaxHeight().padding(horizontal = 10.dp)
            ) {
                Box(modifier = Modifier.weight(0.5f), contentAlignment = Alignment.Center) {
                    Text(col.name(), style = CustomTypography.h5)
                }
                for (currentTeam in teams ?: emptyList()) {
                    when (firstCell) {
                        is Boolean -> {
                            CheckBox(
                                initialData = timData!!.firstOrNull {
                                    it[team] == currentTeam.number &&
                                        it[alliance] == currentTeam.color &&
                                        it[match] == settings!!.match
                                }?.get(col) as Boolean,
                                onChange = { new ->
                                    timData = timData!!.update(col).where {
                                        team() == currentTeam.number &&
                                            alliance() == currentTeam.color &&
                                            match() == settings!!.match
                                    }.with { new }
                                },
                                modifier = Modifier.weight(1f).wrapContentHeight().padding(horizontal = 50.dp)
                            )
                        }

                        is Int -> {
                            NumberPicker(
                                initialData = timData!!.firstOrNull {
                                    it[team] == currentTeam.number &&
                                        it[alliance] == currentTeam.color &&
                                        it[match] == settings!!.match
                                }?.get(col) as Int,
                                onChange = { new ->
                                    timData = timData!!.update(col).where {
                                        team() == currentTeam.number &&
                                            alliance() == currentTeam.color &&
                                            match() == settings!!.match
                                    }.with { new }
                                },
                                modifier = Modifier.weight(1f).wrapContentHeight().fillMaxWidth()
                            )
                        }

                        else -> {
                            TextDataField(
                                initialData = timData!!.firstOrNull {
                                    it[team] == currentTeam.number &&
                                        it[alliance] == currentTeam.color &&
                                        it[match] == settings!!.match
                                }?.get(col)?.toString() ?: "",
                                onChange = { new ->
                                    timData = timData!!.update(col).where {
                                        team() == currentTeam.number &&
                                            alliance() == currentTeam.color &&
                                            match() == settings!!.match
                                    }.with { new }
                                },
                                modifier = Modifier.weight(1f).wrapContentHeight().fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
