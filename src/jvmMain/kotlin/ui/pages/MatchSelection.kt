package ui.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.files.editSettings
import io.files.matchSchedule
import io.files.settings

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MatchSelectionPage() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Select a match")
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            Text("Match Number", modifier = Modifier.weight(1f))
            Text("Blue Teams", modifier = Modifier.weight(3f))
            Text("Red Teams", modifier = Modifier.weight(3f))
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            for ((matchNum, matchObj) in matchSchedule!!.toList().sortedBy { it.first.toIntOrNull() }) item {
                Row(modifier = Modifier.fillMaxWidth().onClick { editSettings { match = matchNum.toInt() } }) {
                    if (matchNum.toIntOrNull() == settings!!.match) {
                        Icon(Icons.Default.Check, null, modifier = Modifier.weight(1f))
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Text(matchNum, modifier = Modifier.weight(1f))
                    Row(modifier = Modifier.weight(3f)) {
                        matchObj.teams.filter { it.color == "blue" }.forEach {
                            Text(it.number, modifier = Modifier.weight(1f))
                        }
                    }
                    Row(modifier = Modifier.weight(3f)) {
                        matchObj.teams.filter { it.color == "red" }.forEach {
                            Text(it.number, modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}
