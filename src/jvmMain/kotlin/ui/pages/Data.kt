package ui.pages

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
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
import io.files.DataType
import io.files.alliance
import io.files.match
import io.files.matchSchedule
import io.files.settings
import io.files.team
import io.files.timData
import io.files.timDataCols
import ui.CheckBox
import ui.NumberPicker
import ui.TextDataField
import ui.theme.CustomTypography

/**
 * The page for entering Team-in-Match data for the current match. All three teams in the current alliance in the
 * current match are shown.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DataPage() = AnimatedContent(targetState = settings) { settings ->
    val teams = matchSchedule!![settings!!.match]?.teams?.filter {
        it.color == settings.alliance
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
        for (col in timDataCols.keys.filter { it.name !in listOf(team, alliance, match) }) {
            Column(
                modifier = Modifier.weight(
                    if (col.type == DataType.Num || col.type == DataType.Bool) 0.5f else 1f
                ).fillMaxHeight().padding(horizontal = 10.dp)
            ) {
                Box(modifier = Modifier.weight(0.5f), contentAlignment = Alignment.Center) {
                    Text(col.name, style = CustomTypography.h5)
                }
                for (currentTeam in teams ?: emptyList()) {
                    val onChange = { new: Any ->
                        timData = timData!!.map {
                            if (it[team] == currentTeam.number &&
                                it[alliance] == currentTeam.color &&
                                it[match] == settings.match
                            ) {
                                it.toMutableMap().apply { set(col.name, new.toString()) }
                            } else {
                                it
                            }
                        }
                    }
                    when (col.type) {
                        DataType.Bool -> {
                            CheckBox(
                                initialData = timData!!.firstOrNull {
                                    it[team] == currentTeam.number &&
                                        it[alliance] == currentTeam.color &&
                                        it[match] == settings.match
                                }?.get(col.name).toBoolean(),
                                onChange = onChange,
                                modifier = Modifier.weight(1f).wrapContentHeight().padding(horizontal = 50.dp)
                            )
                        }

                        DataType.Num -> {
                            NumberPicker(
                                initialData = timData!!.firstOrNull {
                                    it[team] == currentTeam.number &&
                                        it[alliance] == currentTeam.color &&
                                        it[match] == settings.match
                                }?.get(col.name)?.toIntOrNull() ?: 0,
                                onChange = onChange,
                                modifier = Modifier.weight(1f).wrapContentHeight().fillMaxWidth()
                            )
                        }

                        DataType.Str -> {
                            TextDataField(
                                initialData = timData!!.firstOrNull {
                                    it[team] == currentTeam.number &&
                                        it[alliance] == currentTeam.color &&
                                        it[match] == settings.match
                                }?.get(col.name) ?: "",
                                onChange = onChange,
                                modifier = Modifier.weight(1f).wrapContentHeight().fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
