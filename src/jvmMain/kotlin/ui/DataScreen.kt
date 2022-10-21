package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable


fun Columns(x: Int) {

    var text by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var text3 by remember { mutableStateOf("") }
    when (x) {
        0 -> {

            Column(modifier = Modifier.padding(horizontal = 10.dp).fillMaxHeight().padding(top= 90.dp), verticalArrangement = Arrangement.spacedBy(170.dp)) {
                Text("")
                Text("Team 1")
                Text("Team 2")
                Text("Team 3")


            }
        }
        5 -> {
            Column(modifier = Modifier.padding(horizontal = 10.dp).padding(top= 90.dp).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(145.dp)) {
                Text(dataPoints[x])
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
        else -> {
            Column(modifier = Modifier.padding(horizontal = 10.dp).padding(top= 90.dp).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(145.dp)) {
                Text(dataPoints[x])
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.width(80.dp)
                )
                TextField(
                    value = text2,
                    onValueChange = { text2 = it },
                    modifier = Modifier.width(80.dp)
                )
                TextField(
                    value = text3,
                    onValueChange = { text3 = it },
                    modifier = Modifier.width(80.dp)
                )
            }
        }
    }

}

val dataPoints = arrayOf("", "Defense", "Defense Rating", "Shooting Hub", "Time Left to Climb", "Notes")

@Composable

fun DataScreen() {

    Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Columns(0)

        Columns(1)

        Columns(2)

        Columns(3)

        Columns(4)

        Columns(5)



}}



