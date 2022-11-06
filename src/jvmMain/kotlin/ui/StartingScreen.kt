package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import files.editSettings
import files.matchSchedule
import files.settings

@Composable
fun StartingScreen(modifier: Modifier) {
    val text = derivedStateOf { buildList {
        for (i in 0 until 3) {
                add(matchSchedule!![settings!!.match.toString()]?.teams?.filter {
                    it.color == settings!!.alliance
                }?.get(i)?.number?.toString() ?: "?")
        }
    }.joinToString(" ") }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text("Stand Strategist App", modifier = Modifier.padding(50.dp), fontSize = 70.sp)




        TextField(
            value = settings!!.name,
            onValueChange = { editSettings { name = it } },
            label = { Text("Input Name:") },
            textStyle = (TextStyle(fontSize = 80.sp)),
            modifier = Modifier.padding(50.dp)
        )

        TextField(
            value = settings!!.match.toString(),
            onValueChange = { editSettings { match = it.toIntOrNull() ?: 0 } },
            label = { Text("Match Number: ") },
            textStyle = (TextStyle(fontSize = 80.sp)),
            modifier = Modifier.padding(50.dp)
        )
        TextField(
            value = text.value,
            onValueChange = {},
            label = { Text("Teams: " )},
            textStyle = (TextStyle(fontSize = 80.sp)),
            modifier = Modifier.padding(50.dp)

        )


//        Row(horizontalArrangement = Arrangement.spacedBy(50.dp), modifier = Modifier.background(Color.LightGray)) {
//            for (i in 0 until 3) {
//                Text(
//                    matchSchedule!![settings!!.match.toString()]?.teams?.filter {
//                        it.color == settings!!.alliance
//                    }?.get(i)?.number?.toString() ?: "?",
//                    fontSize = 100.sp,
//
//                )
//            }
//        }
    }
}
