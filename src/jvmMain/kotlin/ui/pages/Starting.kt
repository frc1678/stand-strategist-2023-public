package ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
            label = { Text("Your Name:") },
            textStyle = MaterialTheme.typography.h2,
            modifier = Modifier.padding(20.dp)
        )

        TextField(
            value = settings!!.match.toString(),
            onValueChange = { editSettings { match = it.toIntOrNull() ?: 0 } },
            label = { Text("Match Number: ") },
            textStyle = MaterialTheme.typography.h2,
            modifier = Modifier.padding(20.dp)
        )

        Button(
            modifier = Modifier.padding(20.dp).height(100.dp).width(500.dp),
            onClick = {
                if (settings!!.alliance == "blue") {
                    editSettings { alliance = "red" }
                } else if (settings!!.alliance == "red") {
                    editSettings { alliance = "blue" }
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(
                    when (settings!!.alliance) {
                        "red" -> 0xFFFF6961
                        "blue" -> 0xFF1F51FF
                        else -> 0xFF808080
                    }
                ),
                contentColor = Color(0xFFFFFFFF)
            )
        ) {
            Text(
                when (settings!!.alliance) {
                    "red" -> "Red Alliance"
                    "blue" -> "Blue Alliance"
                    else -> "None"
                },
                style = MaterialTheme.typography.h4
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(50.dp)) {
            for (i in 0 until 3) {
                Text(
                    matchSchedule!![settings!!.match.toString()]?.teams?.filter {
                        it.color == settings!!.alliance
                    }?.getOrNull(i)?.number?.toString() ?: "?",
                    style = MaterialTheme.typography.h2
                )
            }
        }
    }
}
