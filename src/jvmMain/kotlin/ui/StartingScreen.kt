package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import files.editSettings
import files.matchSchedule
import files.settings

@Composable
fun StartingScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = settings!!.name,
            onValueChange = { editSettings { name = it } },
            textStyle = TextStyle(fontSize = 200.sp)
        )
        Text(settings!!.match.toString(), fontSize = 200.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(30.dp)) {
            val teams = matchSchedule!![settings!!.match.toString()]!!.teams.filter { it.color == settings!!.alliance }
            teams.forEach {
                Text(it.number.toString(), fontSize = 100.sp)
            }
        }
    }
}
