package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable


fun Columns(x: Int) {
    Text(dataPoints[x])
    var text by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var text3 by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Label") }
        )
        TextField(
            value = text2,
            onValueChange = { text2 = it },
            label = { Text("Label") }
        )
        TextField(
            value = text3,
            onValueChange = { text3 = it },
            label = { Text("Label") }
        )
    }
}

val dataPoints = arrayOf("", "Defense", "Defense Rating", "Shooting Hub", "Time Left to Climb", "Notes")

@Composable

fun DataScreen() {


    var text by remember { mutableStateOf("Hello") }
    Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Columns(0)

        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly) {
            Text("Defense")
            Text("team 1")
            Text("team 2")
            Text("3")
        }

        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly) {
            Text("Defense Rating")
            Text("team 1")
            Text("team 2")
            Text("3")
        }

        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly) {
            Text("Shooting Hub")
            Text("team 1")
            Text("team 2")
            Text("3")
        }

        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly) {
            Text("Time Left to Climb")
            Text("team 1")
            Text("team 2")
            Text("3")
        }

        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly) {
            Text("Notes")
            Text("team 1")
            Text("team 2")
            Text("team 3")
        }
    }

}



