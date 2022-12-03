package ui.pages

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.files.team
import io.files.teamData
import io.files.teamDataCols
import org.jetbrains.kotlinx.dataframe.api.firstOrNull
import org.jetbrains.kotlinx.dataframe.api.update
import org.jetbrains.kotlinx.dataframe.api.where
import org.jetbrains.kotlinx.dataframe.api.with
import ui.TextDataField

@Composable
fun AllNotesPage() {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(0.5f))
            for (col in teamDataCols.keys.filter { it.name() != team.name() }) {
                Text(col.name(), modifier = Modifier.weight(1f))
            }
        }
        Box(modifier = Modifier.weight(1f).fillMaxWidth().padding(vertical = 40.dp)) {
            val listState = rememberLazyListState()
            LazyColumn(verticalArrangement = Arrangement.spacedBy(60.dp), state = listState) {
                teamData!![team].toList().sorted().forEach { currentTeam ->
                    item {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.weight(0.5f), contentAlignment = Alignment.Center) {
                                Text("$currentTeam")
                            }
                            for (col in teamDataCols.keys.filter { it.name() != team.name() }) {
                                TextDataField(
                                    initialData = teamData!!.firstOrNull { row -> row[team] == currentTeam }?.get(col)
                                        ?.toString() ?: "",
                                    onChange = { new ->
                                        teamData = teamData!!.update(col).where { team() == currentTeam }.with { new }
                                    },
                                    modifier = Modifier.weight(1f).padding(horizontal = 5.dp).wrapContentHeight()
                                )
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
