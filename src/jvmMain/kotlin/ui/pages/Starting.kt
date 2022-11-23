package ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.files.editSettings
import io.files.matchSchedule
import io.files.settings

@Composable
fun StartingPage() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = settings!!.name,
            onValueChange = { editSettings { name = it } },
            label = { Text("Input Name:") },
            textStyle = (TextStyle(fontSize = 100.sp)),
            modifier = Modifier.padding(50.dp)
        )

        TextField(
            value = settings!!.match.toString(),
            onValueChange = { editSettings { match = it.toIntOrNull() ?: 0 } },
            label = { Text("Match Number: ") },
            textStyle = (TextStyle(fontSize = 100.sp)),
            modifier = Modifier.padding(50.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(50.dp)) {
            for (i in 0 until 3) {
                Text(
                    matchSchedule!![settings!!.match.toString()]?.teams?.filter {
                        it.color == settings!!.alliance
                    }?.get(i)?.number?.toString() ?: "?",
                    fontSize = 100.sp
                )
            }
        }
    }
}
