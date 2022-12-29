package ui.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.onClick
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.files.editSettings
import io.files.matchSchedule
import io.files.settings
import org.jetbrains.skiko.PredefinedCursors

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MatchSelectionPage() {
    Column(modifier = Modifier.padding(start = 50.dp, end = 50.dp)) {
        Text("Select a match", style = MaterialTheme.typography.h3)
        Spacer(modifier = Modifier.height(30.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                "Match",
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1.5f)
            )
            Text("Blue Teams", style = MaterialTheme.typography.h4, modifier = Modifier.weight(3f))
            Text("Red Teams", style = MaterialTheme.typography.h4, modifier = Modifier.weight(3f))
        }
        Box {
            val listState = rememberLazyListState()
            LazyColumn(state = listState, modifier = Modifier.padding(vertical = 10.dp)) {
                for ((matchNum, matchObj) in matchSchedule!!.toList().sortedBy { it.first.toIntOrNull() }) item {
                    Box(
                        modifier = Modifier.onClick { editSettings { match = matchNum.toInt() } }
                            .pointerHoverIcon(PointerIcon(PredefinedCursors.HAND))
                            .border(
                                width = 3.dp,
                                color = if (matchNum.toIntOrNull() == settings!!.match) {
                                    MaterialTheme.colors.primary
                                } else {
                                    Color.Transparent
                                },
                                shape = RoundedCornerShape(5.dp)
                            )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(vertical = 10.dp)
                        ) {
                            Text(matchNum, textAlign = TextAlign.Center, modifier = Modifier.weight(1.5f))
                            Row(modifier = Modifier.weight(3f)) {
                                matchObj.teams.filter { it.color == "blue" }.forEach {
                                    Text(it.number, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                                }
                            }
                            Row(modifier = Modifier.weight(3f)) {
                                matchObj.teams.filter { it.color == "red" }.forEach {
                                    Text(it.number, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }
            VerticalScrollbar(
                adapter = rememberScrollbarAdapter(scrollState = listState),
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}
