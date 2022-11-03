package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
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
fun DataScreen(modifier: Modifier) {
    Row(modifier = modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly) {
        var text by remember { mutableStateOf("") }
        var text2 by remember { mutableStateOf("") }
        var text3 by remember { mutableStateOf("") }
        val checkedState = remember { mutableStateOf(true) }
        val checkedState2 = remember { mutableStateOf(true) }
        val checkedState3 = remember { mutableStateOf(true) }
        val checkedState4 = remember { mutableStateOf(true) }
        val checkedState5 = remember { mutableStateOf(true) }
        val checkedState6 = remember { mutableStateOf(true) }
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
            verticalArrangement = Arrangement.spacedBy(125.dp)
        ) {
            Text("Defense")
            Checkbox(
                checked = checkedState.value,
                modifier = Modifier.padding(16.dp),
                onCheckedChange = { checkedState.value = it },
            )
            Checkbox(
                checked = checkedState2.value,
                modifier = Modifier.padding(16.dp),
                onCheckedChange = { checkedState2.value = it },
            )
            Checkbox(
                checked = checkedState3.value,
                modifier = Modifier.padding(16.dp),
                onCheckedChange = { checkedState3.value = it },
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 10.dp).padding(top = 90.dp).fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(145.dp)
        ) {
            Text("Defense Rating")
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

        Column(
            modifier = Modifier.padding(horizontal = 10.dp).padding(top = 90.dp).fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(125.dp)
        ) {
            Text("Shooting Hub")
            Checkbox(
                checked = checkedState4.value,
                modifier = Modifier.padding(16.dp),
                onCheckedChange = { checkedState4.value = it },
            )
            Checkbox(
                checked = checkedState5.value,
                modifier = Modifier.padding(16.dp),
                onCheckedChange = { checkedState5.value = it },
            )
            Checkbox(
                checked = checkedState6.value,
                modifier = Modifier.padding(16.dp),
                onCheckedChange = { checkedState6.value = it },
            )
        }
        Column(
            modifier = Modifier.padding(horizontal = 10.dp).padding(top = 90.dp).fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(145.dp)
        ) {
            Text("Time Left to Climb")
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
