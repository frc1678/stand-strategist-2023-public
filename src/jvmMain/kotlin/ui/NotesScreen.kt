package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import files.matchSchedule
import files.settings

@Composable
fun NotesScreen(modifier: Modifier) {
    Row(modifier = modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly) {
        var text by remember { mutableStateOf("") }
        var text2 by remember { mutableStateOf("") }
        var text3 by remember { mutableStateOf("") }
        Column(
            modifier = Modifier.padding(horizontal = 10.dp).fillMaxHeight().padding(top = 90.dp),
            verticalArrangement = Arrangement.spacedBy(170.dp)
        ) {
            Text("")
            for (i in 0 until 3) {
                Text(
                    matchSchedule!![settings!!.match.toString()]!!.teams.filter {
                        it.color == settings!!.alliance
                    }[i].number.toString()
                )
            }
        }
        Column(
            modifier = Modifier.padding(horizontal = 10.dp).padding(top = 90.dp).fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(145.dp)
        ) {
            Text("Notes")
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.width(330.dp)
            )
            TextField(
                value = text2,
                onValueChange = { text2 = it },
                modifier = Modifier.width(330.dp)
            )
            TextField(
                value = text3,
                onValueChange = { text3 = it },
                modifier = Modifier.width(330.dp)
            )
        }
    }
}
