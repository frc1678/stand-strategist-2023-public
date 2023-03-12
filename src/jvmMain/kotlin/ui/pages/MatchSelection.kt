package ui.pages

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.onClick
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.files.editSettings
import io.files.matchSchedule
import io.files.settings
import org.jetbrains.skiko.PredefinedCursors
import ui.navigation.NavGraph
import ui.navigation.navigateTo
import ui.theme.CustomTypography

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@Composable
fun MatchSelectionPage() {
    Column(modifier = Modifier.padding(start = 50.dp, end = 50.dp)) {
        Text("Select a match", style = CustomTypography.h3)
        Spacer(modifier = Modifier.height(30.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                "Match",
                style = CustomTypography.h4,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1.5f)
            )
            Text("Blue Teams", style = CustomTypography.h4, modifier = Modifier.weight(3f))
            Text("Red Teams", style = CustomTypography.h4, modifier = Modifier.weight(3f))
        }
        AnimatedContent(targetState = matchSchedule, modifier = Modifier.weight(1f)) { schedule ->
            Box(modifier = Modifier.fillMaxSize()) {
                val listState = rememberLazyListState()
                LazyColumn(state = listState, modifier = Modifier.padding(vertical = 10.dp)) {
                    for ((matchNum, matchObj) in schedule!!.toList().sortedBy { it.first.toIntOrNull() }) item {
                        var deleting by remember { mutableStateOf(false) }
                        var hovering by remember { mutableStateOf(false) }
                        Box(
                            modifier = Modifier.onClick { editSettings { match = matchNum } }
                                .pointerHoverIcon(PointerIcon(PredefinedCursors.HAND)).border(
                                    width = 3.dp,
                                    color = if (matchNum == settings!!.match) {
                                        MaterialTheme.colors.primary
                                    } else {
                                        Color.Transparent
                                    },
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .onPointerEvent(PointerEventType.Enter) { hovering = true }
                                .onPointerEvent(PointerEventType.Exit) { hovering = false }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.padding(vertical = 20.dp)
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
                            if (hovering && schedule.size > 1) {
                                Box(
                                    contentAlignment = Alignment.CenterStart,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                ) {
                                    if (deleting) {
                                        Row(modifier = Modifier.fillMaxHeight()) {
                                            TextButton(onClick = {
                                                if (schedule.size > 1) {
                                                    if (matchNum == settings!!.match) {
                                                        editSettings {
                                                            match = schedule.toList()
                                                                .first { it.first != matchNum }.first
                                                        }
                                                    }
                                                    matchSchedule =
                                                        matchSchedule!!.toMutableMap().apply { remove(matchNum) }
                                                }
                                                deleting = false
                                            }) {
                                                Text("Confirm", style = CustomTypography.body1)
                                            }
                                            TextButton(onClick = { deleting = false }) {
                                                Text("Cancel", style = CustomTypography.body1)
                                            }
                                        }
                                    } else {
                                        IconButton(onClick = { deleting = true }) {
                                            Icon(Icons.Default.Delete, contentDescription = null)
                                        }
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
        Button(
            onClick = { navigateTo(NavGraph.NEW_MATCH) },
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(30.dp)
        ) {
            Text("New Match...")
        }
    }
}
