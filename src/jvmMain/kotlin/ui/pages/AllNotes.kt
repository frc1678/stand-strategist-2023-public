package ui.pages

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.files.team
import io.files.teamData
import io.files.teamDataCols
import ui.TextDataField
import ui.theme.CustomTypography

/**
 * The page showing the team notes for every team. These team notes are editable and shown in a scrollable list, with a
 * search bar for searching for teams. These are for team notes **across the entire competition**, not match-specific
 * notes.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AllNotesPage() {
    Column {
        var search by remember { mutableStateOf("") }
        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            placeholder = { Text("Search teams...", style = CustomTypography.body1) },
            leadingIcon = { Icon(Icons.Default.Search, "Search") },
            textStyle = CustomTypography.body1,
            modifier = Modifier.fillMaxWidth().padding(bottom = 30.dp, start = 30.dp)
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(0.5f))
            for (col in teamDataCols.keys.filter { it.name != team }) {
                Text(col.name, style = CustomTypography.h5, modifier = Modifier.weight(1f))
            }
        }
        AnimatedContent(search) { currentSearch ->
            Box(modifier = Modifier.weight(1f).fillMaxWidth().padding(vertical = 40.dp)) {
                val listState = rememberLazyListState()
                LazyColumn(verticalArrangement = Arrangement.spacedBy(60.dp), state = listState) {
                    teamData!!.map { it[team]!! }.sortedBy { it.toIntOrNull() }.forEach { currentTeam ->
                        if (currentTeam.contains(currentSearch)) {
                            item {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier.weight(0.5f), contentAlignment = Alignment.Center) {
                                        Text(currentTeam)
                                    }
                                    for (col in teamDataCols.keys.filter { it.name != team }) {
                                        TextDataField(
                                            initialData = teamData!!.firstOrNull { row -> row[team] == currentTeam }
                                                ?.get(col.name) ?: "",
                                            onChange = { new ->
                                                teamData = teamData!!.map {
                                                    if (it[team] == currentTeam) {
                                                        it.toMutableMap()
                                                            .apply { set(col.name, new) }
                                                    } else {
                                                        it
                                                    }
                                                }
                                            },
                                            modifier = Modifier.weight(1f).padding(horizontal = 5.dp)
                                                .wrapContentHeight()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                VerticalScrollbar(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    adapter = rememberScrollbarAdapter(scrollState = listState)
                )
            }
        }
    }
}
